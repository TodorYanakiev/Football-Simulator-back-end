package com.example.FootballManager_back_end.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootballTeamDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private BaseTeamDTO baseTeam;
    private Integer budget;
    private LeagueDTO league;
    private StandingDTO standing;
    private UserDTO user;

    //lineup when it comes time to
}