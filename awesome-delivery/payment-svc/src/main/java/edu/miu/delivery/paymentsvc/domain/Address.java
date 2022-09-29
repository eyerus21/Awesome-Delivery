package edu.miu.delivery.paymentsvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
}
