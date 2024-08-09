package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.Request.LeagueCreateRequest;
import com.example.FootballManager_back_end.Service.LeagueService;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = LeagueController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = LeagueController.class
                )
        })
class LeagueControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeagueService leagueService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLeague_Success() throws Exception {
        LeagueCreateRequest leagueCreateRequest = LeagueCreateRequest.builder()
                .name("Premier League")
                .baseTeamIdList(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L))
                .build();

        String expectedResponse = "League Premier League is created.";

        when(leagueService.createLeague(any(LeagueCreateRequest.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/leagues/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(leagueCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponse))
                .andReturn();
    }

}
