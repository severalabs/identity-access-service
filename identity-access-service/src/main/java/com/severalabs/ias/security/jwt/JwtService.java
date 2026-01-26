package com.severalabs.ias.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

//    @Value("${security.jwt.secret}")
//    private String jwt_secret;
//
//    @Value("${security.jwt.expiration}")
//    private Long jwt_expiration;

    private static final String jwt_secret =
            "THIS_IS_A_VERY_LONG_AND_SECURE_SECRET_KEY_32_CHARS_MIN";

    public Key generateKey() {
        return Keys.hmacShaKeyFor(jwt_secret.getBytes());
    }

    public String generateToken (String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 360000))
                .signWith(generateKey())
                .compact();
    }


    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }



}
