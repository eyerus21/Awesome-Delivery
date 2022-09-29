package edu.miu.delivery.ordersvc.service;

import java.util.Collection;
import java.util.Optional;

import edu.miu.delivery.ordersvc.domain.Order;
import edu.miu.delivery.ordersvc.domain.Order.OrderStatus;

public interface OrderService {
    Order createOrder(Order order);
    void processPayedResponse(String orderId, Boolean response);
    Order cancelOrder(String orderId);
    Optional<Order> getOrder(String orderId);
    Collection<Order> getOrders(String customerId);
    void updateOrderStatus(String orderId, OrderStatus status);
}
