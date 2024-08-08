package com.example.FootballManager_back_end.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "standings")
public class Standing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "football_team_id")
    private FootballTeam footballTeam;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @Min(0)
    private Byte points;

    @Min(0)
    private Short scoredGoals;

    @Min(0)
    private Short concededGoals;

    @Min(0)
    private Byte playedMatches;
}
