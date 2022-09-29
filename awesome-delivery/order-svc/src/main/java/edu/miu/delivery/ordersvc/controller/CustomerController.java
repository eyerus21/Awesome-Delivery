package edu.miu.delivery.ordersvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.delivery.ordersvc.domain.Customer;
import edu.miu.delivery.ordersvc.service.CustomerService;

@RestController
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "topic-user")
    public void listenUser(ConsumerRecord<String, String> cr, @Payload String payload) {
        try {
            Customer customer = objectMapper.readValue(payload, Customer.class);
            if (!customer.getUserType().equals("CUSTOMER")) {
                return;
            }
            logger.info("Received message![key={} Payload={}]", cr.key(), customer);
            customerService.createCustomer(customer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
