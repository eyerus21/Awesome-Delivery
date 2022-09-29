package edu.miu.delivery.paymentsvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.delivery.paymentsvc.domain.Order;
import edu.miu.delivery.paymentsvc.dto.OrderMessage;
import edu.miu.delivery.paymentsvc.dto.PaymentRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PaymentController {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.topic-name}")
    String orderTopic;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @RabbitListener(queues = "#{queue.name}", concurrency = "10")
    public Boolean receive(PaymentRequest request) {
        logger.info("Payment request processed![{}]", request);
        return true;
    }


    @KafkaListener(topics = "topic-order")
    public void orderPayment(ConsumerRecord<String, String> cr, @Payload String payload) {
        Boolean processed = false;
        try {
            OrderMessage order = objectMapper.readValue(payload, OrderMessage.class);
            if(order.getStatus() != Order.OrderStatus.PLACED)
            {
                return;
            }

            logger.info("Payment request processed![{}]", order);
            order.setStatus(Order.OrderStatus.PAYED);

            //kafkaTemplate.send("topic-orderpayment", order.getOrderId(), objectMapper.writeValueAsString(OrderMessage.create(order1)));
            kafkaTemplate.send("topic-orderpayment", order.getOrderId(), objectMapper.writeValueAsString(order));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
