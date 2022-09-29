package edu.miu.delivery.ordersvc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.miu.delivery.ordersvc.domain.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    
}
