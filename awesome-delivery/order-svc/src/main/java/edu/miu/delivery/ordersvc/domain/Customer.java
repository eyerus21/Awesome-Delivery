package edu.miu.delivery.ordersvc.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    @Id
    private String id;
    private String userType;
    private String name;
    private String phoneNumber;
    private String email;
    private Address address;
    private Payment payment;
}
