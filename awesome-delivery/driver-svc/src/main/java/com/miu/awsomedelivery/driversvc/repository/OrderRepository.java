package com.miu.awsomedelivery.driversvc.repository;

import com.miu.awsomedelivery.driversvc.Enum.OrderStatus;
import com.miu.awsomedelivery.driversvc.model.Driver;
import com.miu.awsomedelivery.driversvc.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    @Query(value= "{orderStatus : ?0}")
    Collection<Order> findAllOrdersBYStatus(OrderStatus orderStatus);
}
