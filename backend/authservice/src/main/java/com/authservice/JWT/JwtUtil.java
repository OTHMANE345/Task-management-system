package com.authservice.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
private String secret = "onddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddjndjcb";

@Autowired
private CustomerUserDetailsSErvice customerUserDetailsSErvice;



    public String extractUsername(String token){
    return extractClaims(token, Claims::getSubject);
}

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
}

    private SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(
                secret.getBytes(StandardCharsets.UTF_8)
        );
        return new SecretKeySpec(keyBytes, "HmacSHA256");

    }


public Claims extractAllClaims(String token){
    return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
}

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(String username, String role, Integer id){
    Map<String, Object> claims = new HashMap<>();
    claims.put("role",role);
    claims.put("id",id);
    return createToken(claims, username);
    }
    private String createToken(Map<String, Object> claims, String subject){
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
    final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validate(String token) {
        Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
        String userName = extractUsername(token);
        UserDetails userDetails = customerUserDetailsSErvice.loadUserByUsername(userName);
        final String username = extractUsername(token);
        return (username.equals((userDetails.getUsername())) && !isTokenExpired(token));
    }


    public Integer extractUserID(String token){
        Claims claims = extractAllClaims(token);
        System.out.println(claims);
        return claims.get("id", Integer.class);
    }

    public String extractUserRole(String token){
        Claims claims = extractAllClaims(token);
        System.out.println(claims);
        return claims.get("role", String.class);
    }
}
