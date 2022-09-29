package edu.miu.delivery.ordersvc.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Order {
    public enum OrderStatus {
        PLACED, PAYED, FAILED, CANCELED, ACCEPTED, REJECTED, READY, PICKED, DELIVERED
    }
    @Id
    private String id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime orderDate;

    private String customerId;
    private String restaurantId;
    @JsonIgnore
    private Customer customer;
    private Collection<Menu> menus;
    private OrderStatus status;
    
    public double totalPrice() {
        return menus.stream().mapToDouble(l -> l.getPrice() * l.getQuantity()).sum();
    }
}
