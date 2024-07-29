package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.BaseFootballPlayerDTO;
import com.example.FootballManager_back_end.Enum.Position;
import com.example.FootballManager_back_end.Service.BaseFootballPlayerService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = BaseFootballPlayerController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = BaseFootballPlayerController.class
                )
        })
class BaseFootballPlayerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseFootballPlayerService baseFootballPlayerService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBaseFootballPlayer() throws Exception {
        BaseFootballPlayerDTO baseFootballPlayerDTO =
                new BaseFootballPlayerDTO(1L, "John", "Doe", "USA", (byte) 25, (byte) 10, Position.CB,
                        (byte) 70, (byte) 85, (byte) 90, (byte) 75, (byte) 80, (byte) 65, (byte) 68, (byte) 60);

        when(baseFootballPlayerService.createBaseFootballPlayer(any(BaseFootballPlayerDTO.class)))
                .thenReturn(baseFootballPlayerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/base-football-players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseFootballPlayerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.nationality").value("USA"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.shirtNumber").value(10))
                .andExpect(jsonPath("$.position").value("CB"))
                .andExpect(jsonPath("$.startDefending").value(70))
                .andExpect(jsonPath("$.startSpeed").value(85))
                .andExpect(jsonPath("$.startDribble").value(90))
                .andExpect(jsonPath("$.startScoring").value(75))
                .andExpect(jsonPath("$.startPassing").value(80))
                .andExpect(jsonPath("$.startStamina").value(65))
                .andExpect(jsonPath("$.startPositioning").value(68))
                .andExpect(jsonPath("$.startGoalkeeping").value(60))
                .andReturn();
    }

    @Test
    void testGetAllFootballPlayers() throws Exception {
        BaseFootballPlayerDTO player1 =
                new BaseFootballPlayerDTO(1L, "John", "Doe", "USA", (byte) 25, (byte) 10, Position.CF,
                        (byte) 70, (byte) 85, (byte) 90, (byte) 75, (byte) 80, (byte) 65, (byte) 68, (byte) 60);
        BaseFootballPlayerDTO player2 =
                new BaseFootballPlayerDTO(2L, "Jane", "Doe", "CAN", (byte) 22, (byte) 15, Position.CM,
                        (byte) 60, (byte) 80, (byte) 85, (byte) 70, (byte) 75, (byte) 70, (byte) 65, (byte) 50);

        List<BaseFootballPlayerDTO> playerList = Arrays.asList(player1, player2);

        when(baseFootballPlayerService.getAllBaseFootballPlayers()).thenReturn(playerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/base-football-players")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].firstName").value("John"))
                .andExpect(jsonPath("$.[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.[0].nationality").value("USA"))
                .andExpect(jsonPath("$.[0].age").value(25))
                .andExpect(jsonPath("$.[0].shirtNumber").value(10))
                .andExpect(jsonPath("$.[0].position").value("CF"))
                .andExpect(jsonPath("$.[0].startDefending").value(70))
                .andExpect(jsonPath("$.[0].startSpeed").value(85))
                .andExpect(jsonPath("$.[0].startDribble").value(90))
                .andExpect(jsonPath("$.[0].startScoring").value(75))
                .andExpect(jsonPath("$.[0].startPassing").value(80))
                .andExpect(jsonPath("$.[0].startStamina").value(65))
                .andExpect(jsonPath("$.[0].startPositioning").value(68))
                .andExpect(jsonPath("$.[0].startGoalkeeping").value(60))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].firstName").value("Jane"))
                .andExpect(jsonPath("$.[1].lastName").value("Doe"))
                .andExpect(jsonPath("$.[1].nationality").value("CAN"))
                .andExpect(jsonPath("$.[1].age").value(22))
                .andExpect(jsonPath("$.[1].shirtNumber").value(15))
                .andExpect(jsonPath("$.[1].position").value("CM"))
                .andExpect(jsonPath("$.[1].startDefending").value(60))
                .andExpect(jsonPath("$.[1].startSpeed").value(80))
                .andExpect(jsonPath("$.[1].startDribble").value(85))
                .andExpect(jsonPath("$.[1].startScoring").value(70))
                .andExpect(jsonPath("$.[1].startPassing").value(75))
                .andExpect(jsonPath("$.[1].startStamina").value(70))
                .andExpect(jsonPath("$.[1].startPositioning").value(65))
                .andExpect(jsonPath("$.[1].startGoalkeeping").value(50))
                .andReturn();
    }

    @Test
    void testGetBaseFootballPlayerById() throws Exception {
        BaseFootballPlayerDTO player =
                new BaseFootballPlayerDTO(1L, "John", "Doe", "USA", (byte) 25, (byte) 10, Position.GK,
                        (byte) 70, (byte) 85, (byte) 90, (byte) 75, (byte) 80, (byte) 65, (byte) 68, (byte) 60);

        when(baseFootballPlayerService.getBaseFootballPlayerById(1L)).thenReturn(player);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/base-football-players/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.nationality").value("USA"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.shirtNumber").value(10))
                .andExpect(jsonPath("$.position").value("GK"))
                .andExpect(jsonPath("$.startDefending").value(70))
                .andExpect(jsonPath("$.startSpeed").value(85))
                .andExpect(jsonPath("$.startDribble").value(90))
                .andExpect(jsonPath("$.startScoring").value(75))
                .andExpect(jsonPath("$.startPassing").value(80))
                .andExpect(jsonPath("$.startStamina").value(65))
                .andExpect(jsonPath("$.startPositioning").value(68))
                .andExpect(jsonPath("$.startGoalkeeping").value(60))
                .andReturn();
    }

    @Test
    void testUpdateBaseFootballPlayerById() throws Exception {
        BaseFootballPlayerDTO updatedPlayer =
                new BaseFootballPlayerDTO(1L, "John", "Doe", "USA", (byte) 26, (byte) 11, Position.LB,
                        (byte) 75, (byte) 90, (byte) 95, (byte) 80, (byte) 85, (byte) 70, (byte) 73, (byte) 65);

        when(baseFootballPlayerService.updateBaseFootballPlayer(eq(1L), any(BaseFootballPlayerDTO.class)))
                .thenReturn(updatedPlayer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/base-football-players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPlayer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.nationality").value("USA"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.shirtNumber").value(11))
                .andExpect(jsonPath("$.position").value("LB"))
                .andExpect(jsonPath("$.startDefending").value(75))
                .andExpect(jsonPath("$.startSpeed").value(90))
                .andExpect(jsonPath("$.startDribble").value(95))
                .andExpect(jsonPath("$.startScoring").value(80))
                .andExpect(jsonPath("$.startPassing").value(85))
                .andExpect(jsonPath("$.startStamina").value(70))
                .andExpect(jsonPath("$.startPositioning").value(73))
                .andExpect(jsonPath("$.startGoalkeeping").value(65))
                .andReturn();
    }

    @Test
    void testDeleteBaseFootballPlayerById() throws Exception {
        when(baseFootballPlayerService.deleteBaseFootballPlayer(1L)).thenReturn("Player deleted successfully");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/base-football-players/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Player deleted successfully"))
                .andReturn();
    }

}
