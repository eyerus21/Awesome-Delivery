package edu.miu.delivery.usersvc.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class LoginDTO {
    private String username;
    private String password;

}
