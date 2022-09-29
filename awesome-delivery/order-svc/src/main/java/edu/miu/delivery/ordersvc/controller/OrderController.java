package edu.miu.delivery.ordersvc.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.delivery.ordersvc.domain.Order;
import edu.miu.delivery.ordersvc.domain.Order.OrderStatus;
import edu.miu.delivery.ordersvc.dto.OrderMessage;
import edu.miu.delivery.ordersvc.dto.OrderResponse;
import edu.miu.delivery.ordersvc.service.OrderService;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    // PAYED, CANCELED
    private final Set<OrderStatus> filterStatus = Arrays.asList(OrderStatus.PAYED, OrderStatus.CANCELED).stream()
            .collect(Collectors.toSet());

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public OrderResponse createOrder(@RequestBody Order order) {
        Order newOrder = orderService.createOrder(order);
        logger.info("Order created![order={}]", newOrder);
        return OrderResponse.create(newOrder);
    }

    @GetMapping
    public Collection<OrderResponse> getOrders(@RequestParam String customerId) {
        return orderService.getOrders(customerId).stream().map(o -> OrderResponse.create(o))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
        Optional<Order> order = orderService.getOrder(orderId);
        if (order.isPresent()) {
            return ResponseEntity.ok(OrderResponse.create(order.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String orderId) {
        Order order = orderService.cancelOrder(orderId);
        if (order != null) {
            return ResponseEntity.ok(OrderResponse.create(order));
        }
        return ResponseEntity.badRequest().build();
    }

    @KafkaListener(topics = "topic-order")
    public void listenOrder(ConsumerRecord<String, String> cr, @Payload String payload) {
        try {
            OrderMessage order = objectMapper.readValue(payload, OrderMessage.class);
            if (filterStatus.contains(order.getStatus())) {
                return;
            }
            logger.info("Received message![key={} Payload={}]", cr.key(), order);
            orderService.updateOrderStatus(order.getOrderId(), order.getStatus());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "topic-orderpayment")
    public void listenOrderPayment(ConsumerRecord<String, String> cr, @Payload String payload) {
        try {
            OrderMessage order = objectMapper.readValue(payload, OrderMessage.class);
            if(order.getStatus() != OrderStatus.PAYED)
            {
                return;
            }

            logger.info("Received message, payment confirmed![key={} Payload={}]", cr.key(), order);
            orderService.updateOrderStatus(order.getOrderId(), order.getStatus());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "#{response.name}", concurrency = "10")
    public void receive(Boolean response, Message message){
        String correlationId = message.getMessageProperties().getCorrelationId();
        logger.info("Received payment message![orderId={} message={}]", correlationId, message);
        orderService.processPayedResponse(correlationId, response);
    }

}
