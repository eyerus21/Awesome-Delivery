package com.miu.accounts.service;

import com.miu.accounts.dto.LoginDTO;
import com.miu.accounts.dto.RegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<?> register(RegistrationDTO registrationBody);
    ResponseEntity<?> authenticate(LoginDTO loginBody);
    ResponseEntity<?> loggedInUser();
}
