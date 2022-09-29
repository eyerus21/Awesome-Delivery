package edu.miu.delivery.usersvc.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document(collection = "users")
@TypeAlias("Driver")
public class Driver extends User {

}

