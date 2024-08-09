package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.BaseFootballPlayerDTO;
import com.example.FootballManager_back_end.DTO.FootballPlayerDTO;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Service.FootballTeamService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = FootballTeamController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = FootballTeamController.class
                )
        })
class FootballTeamControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FootballTeamService footballTeamService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPlayersToTeam() throws Exception {
        Long teamId = 1L;
        List<Long> basePlayerIds = Arrays.asList(1L, 2L);

        BaseFootballPlayerDTO baseFootballPlayerDTO1 = new BaseFootballPlayerDTO();
        baseFootballPlayerDTO1.setFirstName("Player1");
        baseFootballPlayerDTO1.setLastName("LastName1");
        FootballPlayerDTO footballPlayerDTO1 = new FootballPlayerDTO();
        footballPlayerDTO1.setId(1L);
        footballPlayerDTO1.setBaseFootballPlayerDTO(baseFootballPlayerDTO1);

        BaseFootballPlayerDTO baseFootballPlayerDTO2 = new BaseFootballPlayerDTO();
        baseFootballPlayerDTO2.setFirstName("Player2");
        baseFootballPlayerDTO2.setLastName("LastName2");
        FootballPlayerDTO footballPlayerDTO2 = new FootballPlayerDTO();
        footballPlayerDTO2.setId(2L);
        footballPlayerDTO2.setBaseFootballPlayerDTO(baseFootballPlayerDTO2);

        List<FootballPlayerDTO> footballPlayerDTOList = Arrays.asList(footballPlayerDTO1, footballPlayerDTO2);

        when(footballTeamService.addPlayersToFootballTeam(eq(teamId), eq(basePlayerIds)))
                .thenReturn(footballPlayerDTOList);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/football-teams/add-players/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basePlayerIds)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].baseFootballPlayerDTO.firstName").value("Player1"))
                .andExpect(jsonPath("$[0].baseFootballPlayerDTO.lastName").value("LastName1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].baseFootballPlayerDTO.firstName").value("Player2"))
                .andExpect(jsonPath("$[1].baseFootballPlayerDTO.lastName").value("LastName2"))
                .andReturn();
    }

    @Test
    void testAddPlayersToTeam_InvalidInput() throws Exception {
        Long teamId = 1L;
        List<Long> basePlayerIds = Arrays.asList(1L, 2L);

        when(footballTeamService.addPlayersToFootballTeam(eq(teamId), eq(basePlayerIds)))
                .thenThrow(new ApiRequestException("Invalid input"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/football-teams/add-players/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basePlayerIds)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}