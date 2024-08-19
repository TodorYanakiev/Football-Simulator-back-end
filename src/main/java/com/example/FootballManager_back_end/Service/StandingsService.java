package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.Response.StandingResponse;
import com.example.FootballManager_back_end.Entity.FootballTeam;
import com.example.FootballManager_back_end.Entity.League;
import com.example.FootballManager_back_end.Entity.Standing;
import com.example.FootballManager_back_end.Repository.FootballTeamRepository;
import com.example.FootballManager_back_end.Repository.LeagueRepository;
import com.example.FootballManager_back_end.Repository.StandingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StandingsService {
    private StandingRepository standingRepository;
    private LeagueRepository leagueRepository;
    private FootballTeamRepository footballTeamRepository;

    public List<StandingResponse> getSortedStandingsByLeagueId(Long leagueId) {
        List<Standing> standings = standingRepository.findSortedStandingsByLeagueId(leagueId);
        return standings.stream()
                .map(s -> new StandingResponse(
                        s.getLeague().getId(),
                        s.getLeague().getName(),
                        s.getFootballTeam().getBaseTeam().getName(),
                        s.getPlayedMatches(),
                        s.getScoredGoals(),
                        s.getConcededGoals(),
                        s.getPoints()))
                .toList();
    }
    public void addStandings(League league) {
        List<FootballTeam> footballTeamList = league.getFootballTeamList();
        List<Standing> standings = new ArrayList<>();
        for (FootballTeam footballTeam : footballTeamList) {
            Standing standing = new Standing();
            standing.setFootballTeam(footballTeam);
            standing.setLeague(league);
            standing.setPoints((byte) 0);
            standing.setScoredGoals((short)0);
            standing.setConcededGoals((short)0);
            standing.setPlayedMatches((byte)0);
            standingRepository.save(standing);
            footballTeam.setStanding(standing);
            footballTeamRepository.save(footballTeam);
            standings.add(standing);
        }
        league.setStandings(standings);
        leagueRepository.save(league);
    }
}
