package com.example.FootballManager_back_end.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootballTeamDTO {
    private Long id;

    private Integer budget;

    private List<FootballPlayerDTO> footballPlayerDTOList;

    private LeagueDTO leagueDTO;

    private StandingDTO standingDTO;

    private UserDTO userDTO;
}