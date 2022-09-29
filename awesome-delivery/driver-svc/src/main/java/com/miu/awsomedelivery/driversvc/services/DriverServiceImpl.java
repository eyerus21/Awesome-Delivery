package com.miu.awsomedelivery.driversvc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.awsomedelivery.driversvc.Enum.OrderStatus;
import com.miu.awsomedelivery.driversvc.model.Driver;
import com.miu.awsomedelivery.driversvc.model.Order;
import com.miu.awsomedelivery.driversvc.repository.DriverRepository;
import com.miu.awsomedelivery.driversvc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepository driverRepository;
    @Autowired
    OrderRepository orderRepository;
    @Value("${app.topic-name}")
    String orderTopic;
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Driver createDriver(Driver driver) {
        return this.driverRepository.save(driver);
    }

    @Override
    public Driver getDriverByID(String id) {
        return this.driverRepository.findById(id).get();
    }

    public Boolean pickOrder(String driverID,String orderID) throws Exception {
        // get driver by ID
        Driver driver = this.driverRepository
                .findById(driverID)
                .get();
        // get order by ID
        Order order = this.orderRepository.findById(orderID).get();
        order.setDriver(driver);
        // set status to picked
        order.setStatus(OrderStatus.PICKED);
        // add to database (Update driver)
        this.orderRepository.save(order);
        // add event to kafka
        String message = objectMapper.writeValueAsString(order);
        kafkaTemplate.send(orderTopic, order.getID(), message);
        return true;
    }

    @Override
    public Boolean deliverOrder(String orderID) throws JsonProcessingException {
        // get order by ID
        Order order = this.orderRepository.findById(orderID).get();
        // set status to delivered
        order.setStatus(OrderStatus.DELIVERED);
        // add to database (Update driver)
        this.orderRepository.save(order);
        // add event to kafka
        String message = objectMapper.writeValueAsString(order);
        //kafkaTemplate.send(orderTopic, order.getID(), message);
        kafkaTemplate.send("topic-orderdelivered", order.getID(), message);
        return true;
    }
}
