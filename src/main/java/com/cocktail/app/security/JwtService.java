package com.cocktail.app.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    private final SecretKey secretKey;

    @Autowired
    public JwtService(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .signWith(secretKey)
                .compact();
    }
}
