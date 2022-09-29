package miu.awesomedelivery.restaurantservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import miu.awesomedelivery.restaurantservice.dto.OrderMessage;
import miu.awesomedelivery.restaurantservice.emuns.OrderStatus;
import miu.awesomedelivery.restaurantservice.model.Order;
import miu.awesomedelivery.restaurantservice.service.OrderServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private OrderServiceImpl orderService;
    @Autowired
    private ObjectMapper objectMapper;



    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
    /***
     * Order listener
     * **/
//    @KafkaListener(topics = "order-topic",groupId = "order-consumer")
    @KafkaListener(topics = "topic-orderpayment")
    public void listenOrder(ConsumerRecord<String, String> cr, @Payload String payload) {
        logger.info("Order is being listened![key={} Payload={}]", cr.key(), payload);
        try {
            OrderMessage orderMessage = objectMapper.readValue(payload,OrderMessage.class);
            if(orderMessage.getStatus().equals(OrderStatus.PAYED)){
                logger.info("Listened message![key={} Payload={}]", cr.key(), payload);
                orderService.createOrder(orderMessage);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("{restId}/order/{orderId}/accept")
    public ResponseEntity<Order> acceptOrder(@PathVariable String restId, @PathVariable String orderId) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.acceptOrder(restId,orderId));
    }

    @PostMapping("{restId}/order/{orderId}/reject")
    public ResponseEntity<Order> rejectOrder(@PathVariable String restId, @PathVariable String orderId) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.rejectOrder(restId,orderId));
    }

    @PostMapping("{restId}/order/{orderId}/ready")
    public ResponseEntity<Order> orderReady(@PathVariable String restId, @PathVariable String orderId) throws JsonProcessingException{
        return ResponseEntity.ok(orderService.orderReady(restId,orderId));
    }

}
