package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Enum.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseFootballPlayerDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
    private Byte age;
    private Byte shirtNumber;
    private Position position;
    private Byte startDefending;
    private Byte startSpeed;
    private Byte startDribble;
    private Byte startScoring;
    private Byte startPassing;
    private Byte startStamina;
    private Byte startPositioning;
    private Byte startGoalkeeping;
}
