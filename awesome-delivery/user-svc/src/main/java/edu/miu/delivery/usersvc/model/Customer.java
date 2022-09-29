package edu.miu.delivery.usersvc.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users")
@TypeAlias("Customer")
public class Customer extends User {
    private Payment payment;
}