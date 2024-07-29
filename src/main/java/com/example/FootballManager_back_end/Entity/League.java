package com.example.FootballManager_back_end.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String name;

    @NotEmpty
    @Size(min = 6)
    private List<Team> teamsLeague;
    //what if u will add a second team later?
    //validate once if "are u sure u will include another team?" if its an odd number and when they press enter
    // to add the league validates if now they are even or odd adn if they are odd is
    // like "please remove a team or add another one "

    //@NotEmpty
    //@Size(min=12) //min teams*2
    // private List <gameWeek> gameWeeksLeague;

    private boolean Started = false; //by default, when it gets added it turns into true?
}
