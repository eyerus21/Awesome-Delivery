package edu.miu.apigateway.utils;

import java.util.Collection;

import lombok.Data;

@Data
public class UserDetail {
    private String username;
    private String userId;
    private Collection<Authority> authorities;
}

