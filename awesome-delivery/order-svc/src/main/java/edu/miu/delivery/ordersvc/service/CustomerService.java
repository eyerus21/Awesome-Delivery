package edu.miu.delivery.ordersvc.service;

import edu.miu.delivery.ordersvc.domain.Customer;

public interface CustomerService {
    Customer getCustomer(String id);
    void createCustomer(Customer customer);
}
