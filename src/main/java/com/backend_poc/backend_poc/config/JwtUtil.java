package com.backend_poc.backend_poc.config;
 
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
 
@Component
public class JwtUtil {
 
    private final String SECRET = "elms_super_secret_key_2024_do_not_share_at_all";
    private final long EXPIRATION = 86400000;
 
    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
 
    public String generateToken(String email, String role, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
 
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }
 
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }
 
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }
 
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}