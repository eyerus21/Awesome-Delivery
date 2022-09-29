package edu.miu.delivery.usersvc.dto;

import edu.miu.delivery.usersvc.model.Address;
import edu.miu.delivery.usersvc.model.Payment;
import lombok.Data;

@Data
public class SignupRequest {
    private String type;
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private Address address;
    private Payment payment;
}
