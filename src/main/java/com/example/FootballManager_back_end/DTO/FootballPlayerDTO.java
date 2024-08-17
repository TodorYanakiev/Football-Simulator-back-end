package com.example.FootballManager_back_end.DTO;

import com.example.FootballManager_back_end.Enum.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootballPlayerDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private BaseFootballPlayerDTO baseFootballPlayer;
    private Byte age;
    private Byte shirtNumber;
    private Position position;
    private FootballTeamDTO footballTeam;
    private Integer price;
    private Byte defending;
    private Byte speed;
    private Byte dribble;
    private Byte scoring;
    private Byte passing;
    private Byte stamina;
    private Byte positioning;
    private Byte goalkeeping;
}
