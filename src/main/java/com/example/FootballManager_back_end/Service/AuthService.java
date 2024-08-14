package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.UserDTO;
import com.example.FootballManager_back_end.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    public UserDTO getUserInfo(){
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String email;
        jwt = authHeader.substring(7);
        email = jwtService.extractUsername(jwt);
        return userService.findUserByEmail(email);
    }
}
