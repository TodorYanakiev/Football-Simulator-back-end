package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.Request.LeagueCreateRequest;
import com.example.FootballManager_back_end.Service.LeagueService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/leagues")
public class LeagueController {
    private final LeagueService leagueService;

    @PostMapping("/create")
    public ResponseEntity<String> createLeague(@Valid @RequestBody LeagueCreateRequest request) {
        String message = leagueService.createLeague(request);
        return ResponseEntity.status(201).body(message);
    }
}
