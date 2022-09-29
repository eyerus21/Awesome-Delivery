package edu.miu.apigateway.service;

import edu.miu.apigateway.utils.Requests;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl {

    @Value("${accounts.baseurl}")
    private String baseUrl;

    @Value("${accounts.register}")
    private String registerUrl;

    @Value("${accounts.login}")
    private String loginUrl;

    public ResponseEntity<?> save(Object registrationBody) {
        return Requests.restMethod(baseUrl+registerUrl, registrationBody, "post");
    }

    public ResponseEntity<?> authenticate(Object loginBody) {
        return Requests.restMethod(baseUrl+loginUrl, loginBody, "post");
    }

    public ResponseEntity<?> getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(principal);
    }
}
