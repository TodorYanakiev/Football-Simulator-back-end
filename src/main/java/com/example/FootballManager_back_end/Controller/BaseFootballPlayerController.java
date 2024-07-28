package com.example.FootballManager_back_end.Controller;

import com.example.FootballManager_back_end.DTO.BaseFootballPlayerDTO;
import com.example.FootballManager_back_end.Service.BaseFootballPlayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/base-football-player")
public class BaseFootballPlayerController {
    private final BaseFootballPlayerService baseFootballPlayerService;

    @PostMapping()
    public ResponseEntity<BaseFootballPlayerDTO> createBaseFootballPlayer(@Valid @RequestBody BaseFootballPlayerDTO baseFootballPlayerDTO) {
        BaseFootballPlayerDTO newBaseFootballPlayerDTO = baseFootballPlayerService.createBaseFootballPlayer(baseFootballPlayerDTO);
        return ResponseEntity.status(201).body(newBaseFootballPlayerDTO);
    }

    @GetMapping()
    public ResponseEntity<List<BaseFootballPlayerDTO>> getAllFootballPlayers() {
        List<BaseFootballPlayerDTO> baseFootballPlayerDTOS = baseFootballPlayerService.getAllBaseFootballPlayers();
        return ResponseEntity.ok(baseFootballPlayerDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseFootballPlayerDTO> getBaseFootballPlayerById(@PathVariable Long id) {
        BaseFootballPlayerDTO baseFootballPlayerDTO = baseFootballPlayerService.getBaseFootballPlayerById(id);
        return ResponseEntity.ok(baseFootballPlayerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseFootballPlayerDTO> updateBaseFootballPlayerById(@PathVariable Long id, @Valid @RequestBody BaseFootballPlayerDTO newBaseFootballPlayerDTO) {
        BaseFootballPlayerDTO baseFootballPlayerDTO = baseFootballPlayerService.updateBaseFootballPlayer(id, newBaseFootballPlayerDTO);
        return ResponseEntity.ok(baseFootballPlayerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBaseFootballPlayerById(@PathVariable Long id) {
        String message = baseFootballPlayerService.deleteBaseFootballPlayer(id);
        return ResponseEntity.ok(message);
    }
}
