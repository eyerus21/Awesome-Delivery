package edu.miu.delivery.ordersvc.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.miu.delivery.ordersvc.domain.Order;
import edu.miu.delivery.ordersvc.domain.Order.OrderStatus;
import edu.miu.delivery.ordersvc.dto.OrderMessage;
import edu.miu.delivery.ordersvc.dto.PaymentRequest;
import edu.miu.delivery.ordersvc.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${app.topic-name}")
    String orderTopic;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    DirectExchange directExchange;

    @Autowired
    Queue replyQueue;

    @Override
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setCustomer(customerService.getCustomer(order.getCustomerId())); // fill customer info
        Order newOrder = orderRepository.save(order);
        //sendPaymentMessage(newOrder);
        sendPaymentMessageKafka(newOrder);
        return newOrder;
    }

    @Override
    public void processPayedResponse(String orderId, Boolean payed) {
        Order order = orderRepository.findById(orderId).get();
        if (!payed) {
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            return;
        }
        order.setStatus(OrderStatus.PAYED);
        orderRepository.save(order);
        try {
            kafkaTemplate.send(orderTopic, order.getId(), objectMapper.writeValueAsString(OrderMessage.create(order)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order cancelOrder(String orderId) {
        Optional<Order> checkOrder = orderRepository.findById(orderId);
        if (!checkOrder.isPresent()) {
            return null;
        }
        Order order = checkOrder.get();
        if (order.getStatus() != OrderStatus.PAYED || order.getStatus() != OrderStatus.FAILED) {
            // we can not cancel orders that are accepted by resturant system
            return null;
        }
        order.setStatus(OrderStatus.CANCELED);
        try {
            kafkaTemplate.send(orderTopic, order.getId(), objectMapper.writeValueAsString(OrderMessage.create(order)));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Collection<Order> getOrders(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void sendPaymentMessage(Order order) {
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setReplyTo(replyQueue.getName());
            messageProperties.setCorrelationId(order.getId());
            return message;
        };
        rabbitTemplate.convertAndSend(directExchange.getName(), "creditcard", PaymentRequest.create(order),
                messagePostProcessor);
    }

    public void sendPaymentMessageKafka(Order order)
    {
        try
        {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            kafkaTemplate.send(orderTopic, order.getId(), objectMapper.writeValueAsString(OrderMessage.create(order)));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
