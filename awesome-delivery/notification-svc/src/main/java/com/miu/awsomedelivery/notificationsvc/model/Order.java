package com.miu.awsomedelivery.notificationsvc.model;

import com.miu.awsomedelivery.notificationsvc.Enum.OrderStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Order {
    String ID;
    private LocalDate date;
    private Double totalPrice;
    Customer customer;
    Driver driver;
    Restaurant restaurant;
    OrderStatus status;
}
