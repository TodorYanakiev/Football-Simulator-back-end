package com.example.FootballManager_back_end.Entity;

import com.example.FootballManager_back_end.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game_weeks")
public class GameWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "weekNumber")
    private Byte weekNumber;

    @OneToMany(mappedBy = "gameWeek")
    private List<FootballMatch> matchList;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @Column(name = "game_week_status")
    @Enumerated(EnumType.STRING)
    private Status gameWeekStatus;
}
