package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.Response.StandingResponse;
import com.example.FootballManager_back_end.Service.StandingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/standings")
public class StandingsController {
    private StandingsService standingsService;

    @GetMapping("/league/{leagueId}")
    public ResponseEntity<List<StandingResponse>> getStandingsForLeague(@PathVariable("leagueId") Long leagueId) {
        try {
            List<StandingResponse> standings = standingsService.getSortedStandingsByLeagueId(leagueId);
            return ResponseEntity.ok(standings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
