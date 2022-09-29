package com.miu.awsomedelivery.notificationsvc.model;

import lombok.Data;

@Data
public class Customer {
     String ID;
     String phoneNumber;
     Address address;
}
