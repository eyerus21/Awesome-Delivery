package com.miu.awsomedelivery.driversvc.model;

import lombok.Data;

@Data
public class Address {
    String country;
    String city;
    String streetAddress;
    String email;
}
