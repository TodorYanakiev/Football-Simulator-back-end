package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Entity.BaseTeam;
import com.example.FootballManager_back_end.Entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String name;
    private List<Team> teamsLeague;
    //private List <gameWeek> gameWeeksLeague;
}
