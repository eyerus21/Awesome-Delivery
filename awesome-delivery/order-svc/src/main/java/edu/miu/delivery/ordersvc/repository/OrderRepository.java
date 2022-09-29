package edu.miu.delivery.ordersvc.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.miu.delivery.ordersvc.domain.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    public Collection<Order> findByCustomerId(String id);
}
