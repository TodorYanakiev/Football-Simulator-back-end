package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.Dto.BaseTeamDTO;
import com.example.FootballManager_back_end.Service.BaseTeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/base-team")
public class BaseTeamController {
    private final BaseTeamService baseTeamService;

    @PostMapping("/add")
    public ResponseEntity<BaseTeamDTO> createBaseFootballTeam(@Valid @RequestBody BaseTeamDTO baseTeamDto) {
        BaseTeamDTO newBaseTeamDto = baseTeamService.createBaseTeam(baseTeamDto);
        return ResponseEntity.status(201).body(newBaseTeamDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BaseTeamDTO>> getAllBaseTeams() {
        List<BaseTeamDTO> baseTeamDTOList = baseTeamService.getAllBaseTeams();
        return ResponseEntity.ok(baseTeamDTOList);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BaseTeamDTO> getBaseTeamById(@PathVariable Long id) throws Exception{
        BaseTeamDTO baseTeamDTO = baseTeamService.getBaseTeamById(id);
        return ResponseEntity.ok(baseTeamDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseTeamDTO> updateBaseTeamById(@PathVariable Long id, @Valid @RequestBody BaseTeamDTO newBaseTeamDto) throws Exception{
        BaseTeamDTO baseTeamDTO = baseTeamService.updateBaseTeam(id, newBaseTeamDto);
        return ResponseEntity.ok(baseTeamDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBaseTeamById(@PathVariable Long id) throws Exception{
        String message = baseTeamService.deleteBaseTeamById(id);
        return ResponseEntity.ok(message);
    }
}
