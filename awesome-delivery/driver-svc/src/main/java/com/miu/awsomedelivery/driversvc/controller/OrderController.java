package com.miu.awsomedelivery.driversvc.controller;

import com.miu.awsomedelivery.driversvc.model.Order;
import com.miu.awsomedelivery.driversvc.services.DriverService;
import com.miu.awsomedelivery.driversvc.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("drivers/{driverID}/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DriverService driverService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // Pick order
    @PostMapping("/{OrderID}/pickOrder")
    public ResponseEntity<Boolean> pickOrder(@RequestParam String driverID, @RequestParam String orderID){
        try {
            return ResponseEntity.ok(this.driverService.pickOrder(driverID,orderID));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Deliver order
    @PostMapping("/{OrderID}/deliverOrder")
    public ResponseEntity<Boolean> deliverOrder(@RequestParam String orderID ){
        try {
            return ResponseEntity.ok(this.driverService.deliverOrder(orderID));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("")
    //get orders which are ready
    public ResponseEntity<Collection<Order>> getReadyOrders(){
        return ResponseEntity.ok(this.orderService.getReadyOrders());
    }
}
