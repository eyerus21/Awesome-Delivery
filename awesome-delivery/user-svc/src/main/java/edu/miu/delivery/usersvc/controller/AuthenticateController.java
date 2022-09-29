package edu.miu.delivery.usersvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.delivery.usersvc.authentication.JWTTokenProvider;
import edu.miu.delivery.usersvc.authentication.UserDetail;

import edu.miu.delivery.usersvc.dto.JWTResponse;
import edu.miu.delivery.usersvc.dto.LoginDTO;

import edu.miu.delivery.usersvc.dto.SignupRequest;
import edu.miu.delivery.usersvc.model.User;
import edu.miu.delivery.usersvc.services.UserService;

@RestController
@RequestMapping("/auth/")
public class AuthenticateController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/check")
    public ResponseEntity<?> checkSecurity(Authentication authentication) {
        UserDetail user = (UserDetail) authentication.getPrincipal();
        return ResponseEntity.ok("Client Check Okay! User = " + user.getUsername()+user.getUserId());
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> authenticateUser(@RequestBody LoginDTO login) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = "";
        try {
            jwt = tokenProvider.generateToken(authentication);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            return ResponseEntity.badRequest().body(new JWTResponse(false,"Invalid credentials!"));
        }
        return ResponseEntity.ok(new JWTResponse(true,jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> customerRegister(@RequestBody SignupRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        User user;
        try {
            if (request.getType().equals("driver")) {
                user = this.userService.addDriver(request);
            } else if (request.getType().equals("restaurant")) {
                user = this.userService.addResturant(request);
            } else {
                user = this.userService.addCustomer(request);
            }
            return ResponseEntity.ok(user);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
