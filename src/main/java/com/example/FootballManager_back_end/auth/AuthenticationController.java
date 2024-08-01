package com.example.FootballManager_back_end.auth;

import com.example.FootballManager_back_end.DTO.UserDTO;
import com.example.FootballManager_back_end.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthService authService;
    //private final LogoutHandler logoutHandler;//new

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/get-info")
    public ResponseEntity<UserDTO> getUserInfo() {
        UserDTO userDTO = authService.getUserInfo();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
