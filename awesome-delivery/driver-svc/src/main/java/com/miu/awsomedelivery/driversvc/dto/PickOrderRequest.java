package com.miu.awsomedelivery.driversvc.dto;

import lombok.Data;

@Data
public class PickOrderRequest {
    private String driverID;
    private String orderID;
}
