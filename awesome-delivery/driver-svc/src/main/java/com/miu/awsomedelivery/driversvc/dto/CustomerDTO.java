package com.miu.awsomedelivery.driversvc.dto;

import com.miu.awsomedelivery.driversvc.model.Address;
import com.miu.awsomedelivery.driversvc.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String id;
    private Address address;
}
