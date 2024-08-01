package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.BaseTeamDTO;
import com.example.FootballManager_back_end.Service.BaseTeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/base-teams")
public class BaseTeamController {
    private final BaseTeamService baseTeamService;

    @PostMapping
    public ResponseEntity<BaseTeamDTO> createBaseFootballTeam(@Valid @RequestBody BaseTeamDTO baseTeamDto) {
        BaseTeamDTO newBaseTeamDto = baseTeamService.createBaseTeam(baseTeamDto);
        return ResponseEntity.status(201).body(newBaseTeamDto);
    }

    @GetMapping
    public ResponseEntity<List<BaseTeamDTO>> getAllBaseTeams() {
        List<BaseTeamDTO> baseTeamDTOList = baseTeamService.getAllBaseTeams();
        return ResponseEntity.ok(baseTeamDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseTeamDTO> getBaseTeamById(@PathVariable Long id) throws Exception{
        BaseTeamDTO baseTeamDTO = baseTeamService.getBaseTeamById(id);
        return ResponseEntity.ok(baseTeamDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseTeamDTO> updateBaseTeamById(@PathVariable Long id, @Valid @RequestBody BaseTeamDTO newBaseTeamDto) throws Exception{
        BaseTeamDTO baseTeamDTO = baseTeamService.updateBaseTeam(id, newBaseTeamDto);
        return ResponseEntity.ok(baseTeamDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBaseTeamById(@PathVariable Long id) throws Exception{
        String message = baseTeamService.deleteBaseTeamById(id);
        return ResponseEntity.ok(message);
    }
}
