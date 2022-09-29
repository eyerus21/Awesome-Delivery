package edu.miu.delivery.paymentsvc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    public enum OrderStatus {
        PLACED, PAYED, FAILED, CANCELED, ACCEPTED, REJECTED, READY, PICKED, DELIVERED
    }

    private String id;

    private LocalDateTime orderDate;
    private String customerId;
    private String restaurantId;
//    @JsonIgnore
    private Customer customer;
    private Collection<Menu> menus;
    private OrderStatus status;

    public double totalPrice() {
        return menus.stream().mapToDouble(l -> l.getPrice() * l.getQuantity()).sum();
    }
}
