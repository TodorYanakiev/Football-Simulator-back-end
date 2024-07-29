package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.Service.BaseFootballPlayerService;
import com.example.FootballManager_back_end.Service.LeagueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/league")
public class LeagueController {
    private final LeagueService leagueService;
}
