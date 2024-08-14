package com.example.FootballManager_back_end.auth;

import com.example.FootballManager_back_end.DTO.Request.AuthenticationRequest;
import com.example.FootballManager_back_end.DTO.Request.RegisterRequest;
import com.example.FootballManager_back_end.DTO.Response.AuthenticationResponse;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Service.UserService;
import com.example.FootballManager_back_end.config.JwtService;
import com.example.FootballManager_back_end.Enum.Role;
import com.example.FootballManager_back_end.Entity.User;
import com.example.FootballManager_back_end.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())//idea = change to get email.
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        if (userService.doesUserExist(user)) throw new ApiRequestException("User with such email already exists!");
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
