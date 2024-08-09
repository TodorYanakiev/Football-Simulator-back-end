package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameWeekDTO {
    private Long id;

    private Byte weekNumber;

    private List<FootballMatchDTO> footballMatchDTOList;

    private LeagueDTO leagueDTO;

    private Status gameWeekStatus;
}
