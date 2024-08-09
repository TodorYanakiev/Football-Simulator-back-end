package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueDTO {
    private Long id;

    private String name;

    private List<FootballTeamDTO> footballTeamDTOList;

    private List<GameWeekDTO> gameWeekDTOList;

    private Status leagueStatus;

    private List<StandingDTO> standingDTOList;
}
