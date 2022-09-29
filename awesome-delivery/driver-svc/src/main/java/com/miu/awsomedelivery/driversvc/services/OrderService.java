package com.miu.awsomedelivery.driversvc.services;

import com.miu.awsomedelivery.driversvc.model.Order;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface OrderService {
    Collection<Order> getReadyOrders();
    void addReadyOrder(Order orderMessage);
}
