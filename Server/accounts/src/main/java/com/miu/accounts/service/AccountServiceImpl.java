package com.miu.accounts.service;

import com.miu.accounts.domain.Account;
import com.miu.accounts.dto.AccountDTO;
import com.miu.accounts.dto.LoginDTO;
import com.miu.accounts.dto.RegistrationDTO;
import com.miu.accounts.repository.AccountRepository;
import com.miu.accounts.util.JwtUtil;
import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private JwtUtil jwtUtil;
    private String secret;
    private String expiry;
    private AuthenticationManager authenticationManager;
    private ModelMapper modelMapper;
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AccountServiceImpl (@Value("${jwt.secret}") String secret, @Value("${jwt.expiry}") String expiry,
                               AccountRepository accountRepository, ModelMapper modelMapper, AuthenticationManager authenticationManager){
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.secret = secret;
        this.expiry = expiry;
    }

    @Override
    public ResponseEntity<?> register(RegistrationDTO registrationBody) {
        if(!validateInputs(registrationBody.getFirstName())){
            JSONObject response = new JSONObject();
            response.put("first name","First name is required");
            return ResponseEntity.badRequest().body(response);
        }
        if(!validateInputs(registrationBody.getLastName())){
            JSONObject response = new JSONObject();
            response.put("last name","Last name is required");
            return ResponseEntity.badRequest().body(response);
        }
        if(!validateInputs(registrationBody.getEmail())){
            JSONObject response = new JSONObject();
            response.put("email","Email is required");
            return ResponseEntity.badRequest().body(response);
        }
        if(!validateInputs(registrationBody.getPassword())){
            JSONObject response = new JSONObject();
            response.put("password","Password is required");
            return ResponseEntity.badRequest().body(response);
        }
        if(!validateInputs(registrationBody.getUsername())){
            JSONObject response = new JSONObject();
            response.put("username","Username is required");
            return ResponseEntity.badRequest().body(response);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(registrationBody.getPassword());
        Account account = new Account();
        account.setFirstName(registrationBody.getFirstName());
        account.setLastName(registrationBody.getLastName());
        account.setEmail(registrationBody.getEmail());
        account.setUsername(registrationBody.getUsername());
        account.setPassword(encodedPassword);
        accountRepository.save(account);
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        return ResponseEntity.ok().body(accountDTO);
    }

    @Override
    public ResponseEntity<?> authenticate(LoginDTO loginBody) {
        if(!validateInputs(loginBody.getUsername())){
            JSONObject response = new JSONObject();
            response.put("username","Username is required");
            return ResponseEntity.badRequest().body(response);
        }
        if(!validateInputs(loginBody.getPassword())){
            JSONObject response = new JSONObject();
            response.put("password","Password is required");
            return ResponseEntity.badRequest().body(response);
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword()));
        }catch (Exception ex){
            JSONObject response = new JSONObject();
            response.put("credentials","Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Account> account = accountRepository.getUserByUsername(loginBody.getUsername());
        String token = jwtUtil.generateToken(account.get().getId(), secret, expiry);
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("token", token);
        return ResponseEntity.ok().body(response);
    }

    public boolean validateInputs(String input){
        if(input == null || input == ""){
            return false;
        }else{
            return true;
        }
    }

    public String getLoggedInUsername(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public ResponseEntity<?> loggedInUser() {
        JSONObject response = new JSONObject();
        Account account = accountRepository.getUserByUsername(getLoggedInUsername()).get();
        if(account != null){
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }else{
            response.put("success",false);
            response.put("message", "No user found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
