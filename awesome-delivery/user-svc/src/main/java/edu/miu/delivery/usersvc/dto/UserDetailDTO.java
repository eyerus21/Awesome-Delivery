package edu.miu.delivery.usersvc.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class UserDetailDTO {
    private String username;
    private String userId;
    private Collection<Authority> authorities;
}
