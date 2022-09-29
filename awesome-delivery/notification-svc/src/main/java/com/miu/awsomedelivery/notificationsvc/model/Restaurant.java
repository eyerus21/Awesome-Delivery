package com.miu.awsomedelivery.notificationsvc.model;

import lombok.Data;

@Data
public class Restaurant {
    String ID;
    String name;
    String phoneNumber;
    String email;
    Address address;
}
