package com.miu.awsomedelivery.notificationsvc.model;

import lombok.Data;

@Data
public class Address {
    String country;
    String city;
    String streetAddress;
    String email;
}
