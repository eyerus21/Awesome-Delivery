package edu.miu.apigateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class JWTTokenProvider implements Serializable {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    private String issuer = "user-svc";
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTTokenProvider.class);

    public UserDetail getUserDetail(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserDetail userDetail;
            try {
                userDetail = objectMapper.readValue(subject, UserDetail.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
            return userDetail;
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public boolean validateToken(String authToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build(); // Reusable verifier instance
            verifier.verify(authToken);
            return true;
        } catch (JWTVerificationException exception) {
            LOGGER.info(exception.getMessage());
        }
        return false;
    }

}

