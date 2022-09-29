package edu.miu.delivery.usersvc.authentication;

import java.io.Serializable;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import edu.miu.delivery.usersvc.dto.UserDetailDTO;

@Component
public class JWTTokenProvider implements Serializable {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    private String issuer = "user-svc";
    @Autowired
    private ObjectMapper objectMapper;
    
    public String generateToken(Authentication authentication) throws JsonProcessingException {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        String subject = objectMapper.writeValueAsString(userDetail);
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create().withIssuer(issuer).withSubject(subject).withIssuedAt(new Date())
                    .withExpiresAt(expiryDate).sign(algorithm);
        } catch (JWTCreationException exception) { // Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
            return "";
        }
    }

    public String getUserUsernameFromJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();
            UserDetailDTO userDetail;
            try {
                userDetail = objectMapper.readValue(subject, UserDetailDTO.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "empty";
            }
            return userDetail.getUsername();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return "empty";
    }

    public boolean validateToken(String authToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build(); // Reusable verifier instance
            verifier.verify(authToken);
            return true;
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return false;
    }

}
