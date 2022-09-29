package com.miu.accounts.controller;

import com.miu.accounts.constants.RestEndpoints;
import com.miu.accounts.domain.Account;
import com.miu.accounts.dto.AccountDTO;
import com.miu.accounts.dto.LoginDTO;
import com.miu.accounts.dto.RegistrationDTO;
import com.miu.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoints.ACCOUNT_PREFIX)
public class AccountController {
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    // Creates an account
    @PostMapping(RestEndpoints.REGISTER)
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationBody){
        return accountService.register(registrationBody);
    }

    // Authenticates a user
    @PostMapping(RestEndpoints.LOGIN)
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginBody){
        return accountService.authenticate(loginBody);
    }

    // Retrieves the current logged in user
    @GetMapping(RestEndpoints.CURRENT_USER)
    public ResponseEntity<?> loggedInUser() {
        return accountService.loggedInUser();
    }


}
