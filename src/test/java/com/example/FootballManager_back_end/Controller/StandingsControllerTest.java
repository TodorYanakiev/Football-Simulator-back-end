package com.example.FootballManager_back_end.Controller;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.FootballManager_back_end.DTO.Response.StandingResponse;
import com.example.FootballManager_back_end.Service.StandingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = StandingsController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = StandingsController.class
                )
        })
class StandingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StandingsService standingsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStandingsForLeagueWhenSuccess() throws Exception {
        StandingResponse standingResponse1 = new StandingResponse(1L, "League A", "Team A", 10, 5, 1, 4);
        StandingResponse standingResponse2 = new StandingResponse(2L, "League B", "Team B", 9, 4, 2, 4);
        List<StandingResponse> standings = Arrays.asList(standingResponse1, standingResponse2);

        when(standingsService.getSortedStandingsByLeagueId(anyLong())).thenReturn(standings);

        mockMvc.perform(get("/api/v1/standings/league/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].leagueId").value(1))
                .andExpect(jsonPath("$[0].leagueName").value("League A"))
                .andExpect(jsonPath("$[0].teamName").value("Team A"))
                .andExpect(jsonPath("$[1].leagueId").value(2))
                .andExpect(jsonPath("$[1].leagueName").value("League B"))
                .andExpect(jsonPath("$[1].teamName").value("Team B"));
    }

    @Test
    void testGetStandingsForLeagueWhenNotFound() throws Exception {
        when(standingsService.getSortedStandingsByLeagueId(anyLong())).thenThrow(new IllegalArgumentException("League not found"));

        mockMvc.perform(get("/api/v1/standings/league/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
