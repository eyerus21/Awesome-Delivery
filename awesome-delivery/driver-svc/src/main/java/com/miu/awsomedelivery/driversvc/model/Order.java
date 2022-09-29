package com.miu.awsomedelivery.driversvc.model;

import com.miu.awsomedelivery.driversvc.Enum.OrderStatus;
import com.miu.awsomedelivery.driversvc.dto.Menu;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class Order {
    @Id
    String ID;
    private LocalDate date;
    private Double totalPrice;
    Customer customer;
    Driver driver;
    Restaurant restaurant;
    OrderStatus status;
    Collection<Menu> menus;
}
