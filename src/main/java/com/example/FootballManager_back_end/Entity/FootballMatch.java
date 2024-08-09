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

    @Column(name = "home_team_score")
    private Short homeTeamScore;

    @Column(name = "away_team_score")
    private Short awayTeamScore;

    @Column(name = "home_attacks")
    private Short homeAttacks;

    @Column(name = "away_attacks")
    private Short awayAttacks;

    @Column(name = "danger_home_attacks")
    private Short dangerHomeAttacks;

    @Column(name = "dangerAwayAttacks")
    private Short dangerAwayAttacks;

    @Column(name = "home_shots")
    private Short homeShots;

    @Column(name = "away_shots")
    private Short awayShots;

    @Column(name = "home_shots_on_target")
    private Short homeShotsOnTarget;

    @Column(name = "away_shots_on_target")
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
