package com.example.FootballManager_back_end.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "base_football_teams")
public class BaseTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 3)
    private String name;
    @NotEmpty
    @Size(min = 2, max = 4)
    private String abbreviation;

    @NotEmpty
    @Size(min = 3)
    private String stadiumName;

    @NotNull
    @Min(0)
    private int startBudget;
}
