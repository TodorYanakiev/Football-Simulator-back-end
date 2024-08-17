package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.Response.StandingResponse;
import com.example.FootballManager_back_end.Entity.Standing;
import com.example.FootballManager_back_end.Repository.StandingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StandingsService {
    private StandingRepository standingRepository;

    public List<StandingResponse> getSortedStandingsByLeagueId(Long leagueId) {
        List<Standing> standings = standingRepository.findSortedStandingsByLeagueId(leagueId);
        return standings.stream()
                .map(s -> new StandingResponse(
                        s.getLeague().getId(),
                        s.getFootballTeam().getBaseTeam().getName(),
                        s.getPlayedMatches(),
                        s.getScoredGoals(),
                        s.getConcededGoals(),
                        s.getPoints()))
                .toList();
    }
}
