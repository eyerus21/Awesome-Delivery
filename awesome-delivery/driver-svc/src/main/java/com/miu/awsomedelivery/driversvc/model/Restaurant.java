package com.miu.awsomedelivery.driversvc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Restaurant {
    @Id
    String ID;
    String name;
    String phoneNumber;
    String email;
    Address address;
}
