package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameWeekDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private Byte weekNumber;
    private LeagueDTO league;
    private Status gameWeekStatus;
}
