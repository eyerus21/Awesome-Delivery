package edu.miu.delivery.ordersvc.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private String street;
    private String city;
    private String state;
    private String email;
    private String country;
}
