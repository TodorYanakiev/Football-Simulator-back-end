package com.example.FootballManager_back_end.Entity;

import com.example.FootballManager_back_end.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matches")
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private FootballTeam homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private FootballTeam awayTeam;

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

    @ManyToOne
    @JoinColumn(name = "game_week_id")
    private GameWeek gameWeek;

    @Column(name = "match_status")
    @Enumerated(EnumType.STRING)
    private Status matchStatus;

    //list of events if it comes time to
}
