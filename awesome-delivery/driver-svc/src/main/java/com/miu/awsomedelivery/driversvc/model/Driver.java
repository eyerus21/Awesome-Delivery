package com.miu.awsomedelivery.driversvc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Driver {
    @Id
    String id;
    String userType;
    String name;
    String phoneNumber;
    String email;
}
