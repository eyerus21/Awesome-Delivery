package com.miu.awsomedelivery.driversvc.services;

import com.miu.awsomedelivery.driversvc.Enum.OrderStatus;
import com.miu.awsomedelivery.driversvc.model.Order;
import com.miu.awsomedelivery.driversvc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Collection<Order> getReadyOrders() {

        return this.orderRepository.findAllOrdersBYStatus(OrderStatus.READY);
    }

    @Override
    public void addReadyOrder(Order orderMessage) {
        this.orderRepository.save(orderMessage);
    }
}
