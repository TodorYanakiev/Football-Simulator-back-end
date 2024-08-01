package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.config.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtService.setSECRET_KEY("c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0");
        jwtService.setJwtExpiration(1000 * 60 * 60 * 24); // 24 hours
    }

    @Test
    void testExtractUsername() {
        UserDetails userDetails = new User("john.doe@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void testExtractClaim() {
        UserDetails userDetails = new User("john.doe@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        assertNotNull(expiration);
    }

    @Test
    void testGenerateToken() {
        UserDetails userDetails = new User("john.doe@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testGenerateTokenWithExtraClaims() {
        UserDetails userDetails = new User("john.doe@example.com", "password", Collections.emptyList());
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "USER");
        String token = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token);
    }

    @Test
    void testIsTokenValid() {
        UserDetails userDetails = new User("john.doe@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testExtractExpiration() {
        UserDetails userDetails = new User("john.doe@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    void testExtractAllClaims() {
        UserDetails userDetails = new User("john.doe@example.com", "password", Collections.emptyList());
        String token = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
    }
}

