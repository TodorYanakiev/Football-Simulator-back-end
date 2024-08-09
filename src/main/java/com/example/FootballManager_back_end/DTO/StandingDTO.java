package com.example.FootballManager_back_end.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandingDTO {
    private Long id;

    private FootballTeamDTO footballTeamDTO;

    private LeagueDTO leagueDTO;

    private Byte points;

    private Short scoredGoals;

    private Short concededGoals;

    private Byte playedMatches;
}
