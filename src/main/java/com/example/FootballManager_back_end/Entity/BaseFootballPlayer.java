package com.example.FootballManager_back_end.Entity;

import com.example.FootballManager_back_end.Enum.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "base_football_players")
@AllArgsConstructor
@NoArgsConstructor
public class BaseFootballPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String firstName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @NotEmpty
    @Size(min = 2)
    private String nationality;

    @NotNull
    @Min(15)
    private Byte age;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte shirtNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Position position;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startDefending;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startSpeed;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startDribble;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startScoring;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startPassing;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startStamina;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startPositioning;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startGoalkeeping;
}
