package com.example.FootballManager_back_end.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTeamDTO {
    private Long id;
    private String name;
    private String abbreviation;
    private String stadiumName;
    private Integer startBudget;
}