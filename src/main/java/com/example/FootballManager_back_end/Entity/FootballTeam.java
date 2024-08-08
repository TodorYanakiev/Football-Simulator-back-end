package com.example.FootballManager_back_end.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="teams")
public class FootballTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String abbreviation;

    private String stadiumName;

    private Integer budget;

    @OneToMany(mappedBy = "footballTeam", fetch = FetchType.EAGER)
    private List<FootballPlayer> playerList;

    @ManyToOne
    @JoinColumn(name="league_id")
    private League league;

    @OneToOne(mappedBy = "footballTeam")
    private Standing standing;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    //lineup when it comes time to
}

