package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.FootballPlayerDTO;
import com.example.FootballManager_back_end.Service.FootballTeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/football-teams")
public class FootballTeamController {
    private final FootballTeamService footballTeamService;

    @PostMapping("/add-players/{teamId}")
    public ResponseEntity<List<FootballPlayerDTO>> addPlayersToTeam(@PathVariable("teamId") Long teamId
            , @RequestBody List<Long> basePlayerIds) {
        List<FootballPlayerDTO> footballPlayerDTOList = footballTeamService.addPlayersToFootballTeam(teamId, basePlayerIds);
        return ResponseEntity.status(201).body(footballPlayerDTOList);
    }
}
