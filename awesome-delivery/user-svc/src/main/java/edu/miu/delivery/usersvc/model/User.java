package edu.miu.delivery.usersvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public abstract class User {
    public enum Role {
        CUSTOMER, DRIVER, RESTAURANT
    }
    public enum UserType {
        CUSTOMER, DRIVER, RESTAURANT
    }
    @Id
    private String id;
    private UserType userType;
    private String username;
    private String password;
    private Set<Role> roles;
    private String name;
    private String phoneNumber;
    private String email;
    private Address address;
}
