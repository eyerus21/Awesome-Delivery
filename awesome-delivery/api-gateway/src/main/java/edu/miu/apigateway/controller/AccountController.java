package edu.miu.apigateway.controller;

import edu.miu.apigateway.constants.RestEndpoints;
import edu.miu.apigateway.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoints.ACCOUNT_PREFIX)
public class AccountController {

    private AccountServiceImpl accountService;
    @Autowired
    public AccountController(AccountServiceImpl accountService){
        this.accountService = accountService;
    }

    // Check if validation is working
    @GetMapping("welcome")
    public ResponseEntity<?> welcome(){
        System.out.println("Awesome delivery is working");
        return ResponseEntity.ok().body("Awesome delivery is working");
    }

    // Add an account
    @PostMapping(RestEndpoints.REGISTER)
    public ResponseEntity<?> register(@RequestBody Object registrationBody){
        return accountService.save(registrationBody);
    }

    // Authenticate a user
    @PostMapping(RestEndpoints.LOGIN)
    public ResponseEntity<?> login(@RequestBody Object loginBody){
        System.out.println("Login body --- "+loginBody);

        return accountService.authenticate(loginBody);
    }

    // Retrieves logged in user info
    @GetMapping(RestEndpoints.CURRENT)
    public ResponseEntity<?> current(){
        return accountService.getLoggedInUser();
    }

}
