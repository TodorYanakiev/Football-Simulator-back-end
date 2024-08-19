package com.example.FootballManager_back_end.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandingDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private FootballTeamDTO footballTeam;
    private LeagueDTO league;
    private Byte points;
    private Short scoredGoals;
    private Short concededGoals;
    private Byte playedMatches;
}
