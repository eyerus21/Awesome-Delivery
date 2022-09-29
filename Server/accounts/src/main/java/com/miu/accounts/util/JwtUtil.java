package com.miu.accounts.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    public static String extractUserId(String token, String secret) {
        return extractClaim(token, Claims::getSubject, secret);
    }

    public static Date extractExpiration(String token, String secret) {
        return extractClaim(token, Claims::getExpiration, secret);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secret) {
        return claimsResolver.apply(extractAllClaims(token, secret));
    }

    private static Claims extractAllClaims(String token, String secret) {
        return  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static String generateToken(String userId,  String secret, String expiry) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, expiry, secret);
    }

    private static String createToken(Map<String, Object> claims, String subject, String expiry, String secret) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(expiry)))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private static Boolean isTokenExpired(String token, String secret) {
        return extractExpiration(token, secret).before(new Date());
    }

    private static Boolean isTokenTrustable(String token, String secret) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public static Boolean isTokenValid(String token, String secret){
        return isTokenTrustable(token, secret) && !isTokenExpired(token, secret);
    }
}
