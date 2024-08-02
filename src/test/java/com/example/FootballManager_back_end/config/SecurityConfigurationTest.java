package com.example.FootballManager_back_end.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableWebSecurity
class SecurityConfigurationTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthFilter;

    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @InjectMocks
    private SecurityConfiguration securityConfiguration;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCorsConfigurationSource() {
        CorsConfigurationSource corsConfigurationSource = securityConfiguration.corsConfigurationSource();
        assertNotNull(corsConfigurationSource);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = (UrlBasedCorsConfigurationSource) corsConfigurationSource;
        CorsConfiguration corsConfiguration = urlBasedCorsConfigurationSource.getCorsConfiguration(new MockHttpServletRequest());

        assertNotNull(corsConfiguration);
        assertNotNull(corsConfiguration.getAllowedOrigins());
        assertNotNull(corsConfiguration.getAllowedMethods());
        assertNotNull(corsConfiguration.getAllowedHeaders());
    }
}

