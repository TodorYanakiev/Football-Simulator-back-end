package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.Dto.BaseTeamDto;
import com.example.FootballManager_back_end.Service.BaseTeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/base-team")
public class BaseTeamController {
    private final BaseTeamService baseTeamService;

    @PostMapping("/add")
    public ResponseEntity<BaseTeamDto> addBaseFootballTeam(@Valid @RequestBody BaseTeamDto baseTeamDto) {
        BaseTeamDto newBaseTeamDto = baseTeamService.addBaseTeam(baseTeamDto);
        return ResponseEntity.ok(newBaseTeamDto);
    }
}
