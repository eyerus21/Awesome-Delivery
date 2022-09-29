package edu.miu.delivery.usersvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTResponse {
    private boolean success;
    private String tokenValue;

}
