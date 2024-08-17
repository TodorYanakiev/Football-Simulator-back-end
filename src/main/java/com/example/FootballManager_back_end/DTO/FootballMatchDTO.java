package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootballMatchDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private FootballTeamDTO homeTeam;
    private FootballTeamDTO awayTeam;
    private Short homeTeamScore;
    private Short awayTeamScore;
    private Short homeAttacks;
    private Short awayAttacks;
    private Short dangerHomeAttacks;
    private Short dangerAwayAttacks;
    private Short homeShots;
    private Short awayShots;
    private Short homeShotsOnTarget;
    private Short awayShotsOnTarget;
    private Byte halfTimesPassed;
    private Byte firstHalfTimeMinutesPassed;
    private Byte secondHalfTimeMinutesPassed;
    private Byte remainingHomeSubstitutions;
    private Byte remainingAwaySubstitutions;
    private Byte stadiumName;
    private GameWeekDTO gameWeek;
    private Status matchStatus;

    //list of events if it comes time to
}
