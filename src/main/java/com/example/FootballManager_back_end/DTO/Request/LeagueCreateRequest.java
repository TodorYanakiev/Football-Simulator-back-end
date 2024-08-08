package com.example.FootballManager_back_end.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeagueCreateRequest {
    private String name;

    private List<Long> baseTeamIdList;
}
