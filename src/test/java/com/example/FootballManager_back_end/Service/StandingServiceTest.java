package com.example.FootballManager_back_end.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.FootballManager_back_end.DTO.Response.StandingResponse;
import com.example.FootballManager_back_end.Entity.BaseTeam;
import com.example.FootballManager_back_end.Entity.FootballTeam;
import com.example.FootballManager_back_end.Entity.League;
import com.example.FootballManager_back_end.Entity.Standing;
import com.example.FootballManager_back_end.Repository.FootballTeamRepository;
import com.example.FootballManager_back_end.Repository.LeagueRepository;
import com.example.FootballManager_back_end.Repository.StandingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class StandingServiceTest {

    @Mock
    private StandingRepository standingRepository;
    @Mock
    private FootballTeamRepository footballTeamRepository;

    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private StandingsService standingsService;

    private League league;
    private List<FootballTeam> footballTeamList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        league = new League();
        footballTeamList = new ArrayList<>();

        FootballTeam team1 = new FootballTeam();
        FootballTeam team2 = new FootballTeam();

        footballTeamList.add(team1);
        footballTeamList.add(team2);

        league.setFootballTeamList(footballTeamList);
    }

    @Test
    void testAddStandings() {
        standingsService.addStandings(league);
        verify(leagueRepository).save(league);
        verify(standingRepository, times(2)).save(any(Standing.class));
        verify(footballTeamRepository, times(2)).save(any(FootballTeam.class));
    }

    @Test
    void testGetSortedStandingsByLeagueIdWhenValidLeagueId() {
        Long leagueId = 1L;
        Standing standing = new Standing();

        League league = new League();
        league.setId(leagueId);
        league.setName("Premier League");

        BaseTeam teamA = new BaseTeam();
        teamA.setName("Team A");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setBaseTeam(teamA);
        footballTeam.setLeague(league);

        standing.setLeague(league);
        standing.setFootballTeam(footballTeam);
        standing.setPlayedMatches((byte) 10);
        standing.setScoredGoals((short) 20);
        standing.setConcededGoals((short) 5);
        standing.setPoints((byte) 30);

        List<Standing> standings = List.of(standing);
        when(standingRepository.findSortedStandingsByLeagueId(leagueId)).thenReturn(standings);

        List<StandingResponse> responses = standingsService.getSortedStandingsByLeagueId(leagueId);

        assertEquals(1, responses.size());
        StandingResponse response = responses.get(0);
        assertEquals(leagueId, response.getLeagueId());
        assertEquals("Premier League", response.getLeagueName());
        assertEquals("Team A", response.getTeamName());
        assertEquals(10, response.getPlayedMatches());
        assertEquals(20, response.getScoredGoals());
        assertEquals(5, response.getConcededGoals());
        assertEquals(30, response.getPoints());
    }

    @Test
    void testGetSortedStandingsByLeagueIdWhenEmptyList() {
        Long leagueId = 1L;
        when(standingRepository.findSortedStandingsByLeagueId(leagueId)).thenReturn(new ArrayList<>());
        List<StandingResponse> responses = standingsService.getSortedStandingsByLeagueId(leagueId);
        assertTrue(responses.isEmpty());
    }

    @Test
    void testRepositoryCall() {
        Long leagueId = 1L;
        standingsService.getSortedStandingsByLeagueId(leagueId);
        verify(standingRepository).findSortedStandingsByLeagueId(leagueId);
    }

    @Test
    void testGetSortedStandingsByLeagueIdWhenNullLeagueId() {
        Long leagueId = null;
        when(standingRepository.findSortedStandingsByLeagueId(leagueId)).thenReturn(new ArrayList<>());
        List<StandingResponse> responses = standingsService.getSortedStandingsByLeagueId(leagueId);
        assertTrue(responses.isEmpty());
    }
}
