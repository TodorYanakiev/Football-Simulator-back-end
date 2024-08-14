package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.UserDTO;
import com.example.FootballManager_back_end.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserInfo() {
        String token = "Bearer test.jwt.token";
        String jwt = "test.jwt.token";
        String email = "john.doe@example.com";
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtService.extractUsername(jwt)).thenReturn(email);
        when(userService.findUserByEmail(email)).thenReturn(userDTO);

        UserDTO result = authService.getUserInfo();

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtService, times(1)).extractUsername(jwt);
        verify(userService, times(1)).findUserByEmail(email);
    }

    @Test
    void testGetUserInfo_NoAuthHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> authService.getUserInfo());

        verify(request, times(1)).getHeader("Authorization");
        verify(jwtService, never()).extractUsername(anyString());
        verify(userService, never()).findUserByEmail(anyString());
    }
}

