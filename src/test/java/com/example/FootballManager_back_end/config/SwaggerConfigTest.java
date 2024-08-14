package com.example.FootballManager_back_end.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    @InjectMocks
    private SwaggerConfig swaggerConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOpenAPI() {
        OpenAPI openAPI = swaggerConfig.openAPI();

        assertNotNull(openAPI);
        assertEquals("Football Simulator", openAPI.getInfo().getTitle());
        assertEquals("REST API for football simulator.", openAPI.getInfo().getDescription());

        assertNotNull(openAPI.getComponents().getSecuritySchemes().get("Bearer Authentication"));
        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("Bearer Authentication");

        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType());
        assertEquals("bearer", securityScheme.getScheme());
        assertEquals("JWT", securityScheme.getBearerFormat());

        assertNotNull(openAPI.getSecurity());
        SecurityRequirement securityRequirement = openAPI.getSecurity().get(0);
        assertEquals(1, securityRequirement.size());
        assertNotNull(securityRequirement.get("Bearer Authentication"));
    }
}
