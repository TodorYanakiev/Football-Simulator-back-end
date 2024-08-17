package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.StandingDTO;
import com.example.FootballManager_back_end.Entity.Standing;
import com.example.FootballManager_back_end.Repository.StandingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StandingsService {
    private StandingRepository standingRepository;
    private final ModelMapper modelMapper;

    public StandingDTO standingToStandingDTO(Standing standing) {
        return modelMapper.map(standing, StandingDTO.class);
    }

    public List<StandingDTO> getSortedStandingsByLeagueId(Long leagueId) {
        List<Standing> standings = standingRepository.findSortedStandingsByLeagueId(leagueId);
        return standings
                .stream()
                .map(this::standingToStandingDTO)
                .toList();
    }
}
