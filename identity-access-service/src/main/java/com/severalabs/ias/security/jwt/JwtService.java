package com.severalabs.ias.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
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
//----------------------------------------------------------------------------------------------------------------------
    private static final String jwt_secret =
            "THIS_IS_A_VERY_LONG_AND_SECURE_SECRET_KEY_32_CHARS_MIN";

    public String getJwtTokenFromRequest (HttpServletRequest request) {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer "))
                return token.substring(7);
            throw new IllegalArgumentException("User not Recognised");
    }

//----------------------------------------------------------------------------------------------------------------------
    public Key generateKey() {
        return Keys.hmacShaKeyFor(jwt_secret.getBytes());
    }

//----------------------------------------------------------------------------------------------------------------------
    public String generateTokenUsingEmail (String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 360000))
                .signWith(generateKey())
                .compact();
    }

//----------------------------------------------------------------------------------------------------------------------
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) generateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//----------------------------------------------------------------------------------------------------------------------
    public Boolean isTokenValid(String token, String email) {
        return getUsernameFromToken (token).equalsIgnoreCase(email);
    }

//----------------------------------------------------------------------------------------------------------------------

}
