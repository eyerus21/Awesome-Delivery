package com.miu.accounts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
