package com.example.FootballManager_back_end.config;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        String token = "Bearer valid.jwt.token";
        String username = "john.doe@example.com";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(userDetails.getAuthorities()).thenReturn(null);
        when(jwtService.extractUsername("valid.jwt.token")).thenReturn(username);
        when(jwtService.isTokenValid("valid.jwt.token", userDetails)).thenReturn(true);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(userDetails, authentication.getPrincipal());
        verify(jwtService, times(1)).extractUsername("valid.jwt.token");
        verify(jwtService, times(1)).isTokenValid("valid.jwt.token", userDetails);
    }

    @Test
    void testDoFilterInternal_InvalidToken_MalformedJwtException() throws ServletException, IOException {
        String token = "Bearer invalid.jwt.token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        when(jwtService.extractUsername("invalid.jwt.token")).thenThrow(MalformedJwtException.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
        verify(jwtService, times(1)).extractUsername("invalid.jwt.token");
    }

    @Test
    void testDoFilterInternal_InvalidToken_ExpiredJwtException() throws ServletException, IOException {
        String token = "Bearer expired.jwt.token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        when(jwtService.extractUsername("expired.jwt.token")).thenThrow(ExpiredJwtException.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
        verify(jwtService, times(1)).extractUsername("expired.jwt.token");
    }

    @Test
    void testDoFilterInternal_InvalidToken_UnsupportedJwtException() throws ServletException, IOException {
        String token = "Bearer unsupported.jwt.token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        when(jwtService.extractUsername("unsupported.jwt.token")).thenThrow(UnsupportedJwtException.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
        verify(jwtService, times(1)).extractUsername("unsupported.jwt.token");
    }

    @Test
    void testDoFilterInternal_InvalidToken_IllegalArgumentException() throws ServletException, IOException {
        String token = "Bearer illegal.jwt.token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        when(jwtService.extractUsername("illegal.jwt.token")).thenThrow(IllegalArgumentException.class);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
        verify(jwtService, times(1)).extractUsername("illegal.jwt.token");
    }
}

