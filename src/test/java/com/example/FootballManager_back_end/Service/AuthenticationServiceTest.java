package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.DTO.Request.AuthenticationRequest;
import com.example.FootballManager_back_end.DTO.Response.AuthenticationResponse;
import com.example.FootballManager_back_end.auth.AuthenticationService;
import com.example.FootballManager_back_end.DTO.Request.RegisterRequest;
import com.example.FootballManager_back_end.config.JwtService;
import com.example.FootballManager_back_end.Enum.Role;
import com.example.FootballManager_back_end.Entity.User;
import com.example.FootballManager_back_end.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterWithNewUser() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setUsername("johndoe");
        request.setPassword("password");

        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userService.doesUserExist(any(User.class))).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(request);

        verify(userService, times(1)).doesUserExist(any(User.class));
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));

        Assertions.assertEquals("jwtToken", response.getToken());
    }

    @Test
    void testRegisterWithExistingUser() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setUsername("johndoe");
        request.setPassword("password");

        // Mock the userService to return true for doesUserExist
        when(userService.doesUserExist(any(User.class))).thenReturn(true);

        // Verify that an ApiRequestException is thrown
        Assertions.assertThrows(ApiRequestException.class, () -> {
            authenticationService.register(request);
        });

        // Verify that the correct methods are called/not called
        verify(userService, times(1)).doesUserExist(any(User.class));
    }


    @Test
    void testAuthenticateWithValidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(request);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).generateToken(any(User.class));

        Assertions.assertEquals("jwtToken", response.getToken());
    }

    @Test
    void testAuthenticateWithInvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("invalidPassword");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> authenticationService.authenticate(request));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, never()).generateToken(any(User.class));
    }
}

