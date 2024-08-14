package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.BaseTeamDTO;
import com.example.FootballManager_back_end.Service.BaseTeamService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = BaseTeamController.class,
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = BaseTeamController.class
                )
        })
class BaseTeamControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseTeamService baseTeamService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBaseTeam() throws Exception {
        BaseTeamDTO baseTeamDTO =
                new BaseTeamDTO(null, "Torpedo", "TOR", "SharkPool", 123450);

        BaseTeamDTO createdBaseTeamDTO =
                new BaseTeamDTO(1L, "Torpedo", "TOR", "SharkPool", 123450);

        when(baseTeamService.createBaseTeam(any(BaseTeamDTO.class))).thenReturn(createdBaseTeamDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/base-teams")
                        .content(objectMapper.writeValueAsString(baseTeamDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Torpedo"))
                .andExpect(jsonPath("$.abbreviation").value("TOR"))
                .andExpect(jsonPath("$.stadiumName").value("SharkPool"))
                .andExpect(jsonPath("$.startBudget").value(123450))
                .andReturn();
    }

    @Test
    void testGetAllBaseTeams() throws Exception {
        BaseTeamDTO baseTeamDTO1 =
                new BaseTeamDTO(1L, "Torpedo", "TOR", "SharkPool", 123456);
        BaseTeamDTO baseTeamDTO2 =
                new BaseTeamDTO(2L, "CSKA", "CSKA", "BulgarianArmy", 12345);

        List<BaseTeamDTO> baseTeamDTOList = Arrays.asList(baseTeamDTO1, baseTeamDTO2);

        when(baseTeamService.getAllBaseTeams()).thenReturn(baseTeamDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/base-teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("Torpedo"))
                .andExpect(jsonPath("$.[0].abbreviation").value("TOR"))
                .andExpect(jsonPath("$.[0].stadiumName").value("SharkPool"))
                .andExpect(jsonPath("$.[0].startBudget").value(123456))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("CSKA"))
                .andExpect(jsonPath("$.[1].abbreviation").value("CSKA"))
                .andExpect(jsonPath("$.[1].stadiumName").value("BulgarianArmy"))
                .andExpect(jsonPath("$.[1].startBudget").value(12345))
                .andReturn();
    }

    @Test
    void testGetBaseTeamById() throws Exception {
        Long baseTeamId = 1L;
        BaseTeamDTO baseTeamDTO =
                new BaseTeamDTO(1L, "Torpedo", "TOR", "SharkPool", 123456);

        when(baseTeamService.getBaseTeamById(baseTeamId)).thenReturn(baseTeamDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/base-teams/{id}", baseTeamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Torpedo"))
                .andExpect(jsonPath("$.abbreviation").value("TOR"))
                .andExpect(jsonPath("$.stadiumName").value("SharkPool"))
                .andExpect(jsonPath("$.startBudget").value(123456))
                .andReturn();
    }

    @Test
    void testUpdateBaseTeamById() throws Exception {
        Long baseTeamId = 1L;

        BaseTeamDTO updatedBaseTeamDTO =
                new BaseTeamDTO(baseTeamId, "UpdatedTorpedo", "UPD", "UpdatedPool", 1234567);

        when(baseTeamService.updateBaseTeamById(anyLong(), any(BaseTeamDTO.class))).thenReturn(updatedBaseTeamDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/base-teams/{id}", baseTeamId)
                        .content(objectMapper.writeValueAsString(updatedBaseTeamDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(baseTeamId))
                .andExpect(jsonPath("$.name").value("UpdatedTorpedo"))
                .andExpect(jsonPath("$.abbreviation").value("UPD"))
                .andExpect(jsonPath("$.stadiumName").value("UpdatedPool"))
                .andExpect(jsonPath("$.startBudget").value(1234567))
                .andReturn();
    }

    @Test
    void testDeleteBaseTeamById() throws Exception {
        Long baseTeamId = 1L;
        String expectedMessage = "Base team with id " + baseTeamId + " deleted successfully.";

        when(baseTeamService.deleteBaseTeamById(baseTeamId)).thenReturn(expectedMessage);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/base-teams/{id}", baseTeamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage))
                .andReturn();
    }
}
