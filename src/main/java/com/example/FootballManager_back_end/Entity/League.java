package com.example.FootballManager_back_end.Entity;

import com.example.FootballManager_back_end.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leagues")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Size(min = 3)
    private String name;

    @OneToMany(mappedBy = "league")
    private List<FootballTeam> footballTeamList;

    @OneToMany(mappedBy = "league")
    private List<GameWeek> gameWeekList;

    @Column(name = "league_status")
    @Enumerated(EnumType.STRING)
    private Status leagueStatus;

    @OneToMany(mappedBy = "league")
    private List<Standing> standings;
}


