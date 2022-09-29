package com.miu.awsomedelivery.driversvc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {
     @Id
     String ID;
     String phoneNumber;
     Address address;
}
