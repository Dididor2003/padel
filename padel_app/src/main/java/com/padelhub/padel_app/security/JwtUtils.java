package com.padelhub.padel_app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

// S'encarrega de crear, llegir i validar tokens
@Component
public class JwtUtils {

	// del .properties
	
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getKey() {
    	// converteix el secret en una clau criptogràfica que entén la llibreria JWT
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Crea un token JWT per a un usuari. Es truca quan lusuari fa login correctament.
    public String generateToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);
        
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getKey())
                .compact();
    }

    // Fa el procés invers
    public String extractEmail(String token) {
    	
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // intenta extreure el correu electrònic, si no llança excepció
    public boolean isTokenValid(String token) {
    	
        try {
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
