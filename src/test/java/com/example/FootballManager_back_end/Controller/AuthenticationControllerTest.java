package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.UserDTO;
import com.example.FootballManager_back_end.Service.AuthService;
import com.example.FootballManager_back_end.auth.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = AuthenticationController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = AuthenticationController.class
                )
        })
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("password")
                .build();

        AuthenticationResponse response = new AuthenticationResponse("token");

        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .content(objectMapper.writeValueAsString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andReturn();
    }

    @Test
    void testAuthenticate() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("johndoe", "password");
        AuthenticationResponse response = new AuthenticationResponse("token");

        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .content(objectMapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"))
                .andReturn();
    }

    @Test
    void testGetUserInfo() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "John", "Doe", "johndoe", "john.doe@example.com");

        when(authService.getUserInfo()).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/get-info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andReturn();
    }
}
