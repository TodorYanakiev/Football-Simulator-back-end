package com.example.FootballManager_back_end.Entity;

import com.example.FootballManager_back_end.Enum.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players")
public class FootballPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "base_player_id")
    private BaseFootballPlayer baseFootballPlayer;

    private Byte age;

    private Byte shirtNumber;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "football_team_id")
    private FootballTeam footballTeam;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte defending;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte speed;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte dribble;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte scoring;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte passing;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte stamina;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte positioning;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte goalkeeping;
}
