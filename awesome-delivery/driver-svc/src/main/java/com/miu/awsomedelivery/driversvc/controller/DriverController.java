package com.miu.awsomedelivery.driversvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.awsomedelivery.driversvc.Enum.OrderStatus;
import com.miu.awsomedelivery.driversvc.dto.CustomerDTO;
import com.miu.awsomedelivery.driversvc.dto.OrderMessage;
import com.miu.awsomedelivery.driversvc.dto.RestaurantDTO;
import com.miu.awsomedelivery.driversvc.model.Customer;
import com.miu.awsomedelivery.driversvc.model.Driver;
import com.miu.awsomedelivery.driversvc.model.Order;
import com.miu.awsomedelivery.driversvc.model.Restaurant;
import com.miu.awsomedelivery.driversvc.services.DriverService;
import com.miu.awsomedelivery.driversvc.services.OrderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);
    // READY
    private final Set<OrderStatus> filterStatus = Arrays.asList(OrderStatus.PLACED).stream()
            .collect(Collectors.toSet());


    // create Driver
    @PostMapping("/drivers")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver){
        return ResponseEntity.ok(this.driverService.createDriver(driver));
    }

    // read driver by ID
    @GetMapping("drivers/{ID}")
    public ResponseEntity<Driver> getDriverByID(@RequestParam String ID){
        return ResponseEntity.ok(this.driverService.getDriverByID(ID));
    }

    // Ready status read
    @KafkaListener(topics = "topic-order")
    public void listenOrder(ConsumerRecord<String, String> cr, @Payload String payload) {
        try {
            OrderMessage orderMessage = objectMapper.readValue(payload, OrderMessage.class);
            if (!filterStatus.contains(orderMessage.getStatus())) {
                return;
            }
            logger.info("Received Order [key={} Payload={}]", cr.key(), orderMessage);
            //addReadyOrder

            Order order = new Order();
            order.setID(orderMessage.getOrderId());
            order.setStatus(orderMessage.getStatus());

            CustomerDTO customerdto = orderMessage.getCustomer();
            Customer customer = new Customer();
            customer.setID(customerdto.getId());
            customer.setAddress(customerdto.getAddress());
            order.setCustomer(customer);
            order.setTotalPrice(orderMessage.getTotalPrice());
            order.setMenus(orderMessage.getMenus());

            RestaurantDTO restaurantDTO = orderMessage.getRestaurant();
            Restaurant restaurant = new Restaurant();
            restaurant.setID(restaurantDTO.getId());
            order.setRestaurant(restaurant);

            this.orderService.addReadyOrder(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }

    // Create driver
    @KafkaListener(topics = "topic-user")
    public void listenDriver(ConsumerRecord<String, String> cr, @Payload String payload) {
        try {
            Driver driver = objectMapper.readValue(payload,Driver.class);
            if (!driver.getUserType().equals("DRIVER")) {
                return;
            }
            logger.info("Received Driver [key={} Payload={}]", cr.key(), driver);
            //addReadyDriver
            this.driverService.createDriver(driver);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }
}
