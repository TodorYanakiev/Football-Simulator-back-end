package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.BaseTeamDTO;
import com.example.FootballManager_back_end.DTO.Request.LeagueCreateRequest;
import com.example.FootballManager_back_end.Entity.League;
import com.example.FootballManager_back_end.Enum.Status;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.FootballTeamRepository;
import com.example.FootballManager_back_end.Repository.LeagueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class LeagueServiceTest {

    @InjectMocks
    private LeagueService leagueService;

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private FootballTeamRepository footballTeamRepository;

    @Mock
    private BaseTeamService baseTeamService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLeague() {
        LeagueCreateRequest request = LeagueCreateRequest.builder()
                .name("Premier League")
                .baseTeamIdList(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L))
                .build();

        League league = new League();
        league.setName("Premier League");
        league.setLeagueStatus(Status.NOT_STARTED);

        BaseTeamDTO baseTeamDTO1 = new BaseTeamDTO(1L, "Team1", "T1", "Stadium1", 100000);
        BaseTeamDTO baseTeamDTO2 = new BaseTeamDTO(2L, "Team2", "T2", "Stadium2", 150000);

        when(leagueRepository.save(any(League.class))).thenReturn(league);
        when(baseTeamService.getBaseTeamById(anyLong())).thenReturn(baseTeamDTO1, baseTeamDTO2);

        String result = leagueService.createLeague(request);

        verify(leagueRepository, times(1)).save(any(League.class));
        verify(footballTeamRepository, times(1)).saveAll(anyList());
        Assertions.assertEquals("League Premier League is created.", result);
    }

    @Test
    void testCreateLeagueWithInvalidNameThrowsException() {
        LeagueCreateRequest request = LeagueCreateRequest.builder()
                .name("PL")
                .baseTeamIdList(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L))
                .build();

        Assertions.assertThrows(ApiRequestException.class, () -> leagueService.createLeague(request));
        verify(leagueRepository, never()).save(any(League.class));
        verify(footballTeamRepository, never()).saveAll(anyList());
    }

    @Test
    void testCreateLeagueWithLessThanSixTeamsThrowsException() {
        LeagueCreateRequest request = LeagueCreateRequest.builder()
                .name("Premier League")
                .baseTeamIdList(Arrays.asList(1L, 2L, 3L, 4L, 5L))
                .build();

        Assertions.assertThrows(ApiRequestException.class, () -> leagueService.createLeague(request));
        verify(leagueRepository, never()).save(any(League.class));
        verify(footballTeamRepository, never()).saveAll(anyList());
    }

    @Test
    void testCreateLeagueWithOddNumberOfTeamsThrowsException() {
        LeagueCreateRequest request = LeagueCreateRequest.builder()
                .name("Premier League")
                .baseTeamIdList(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L))
                .build();

        Assertions.assertThrows(ApiRequestException.class, () -> leagueService.createLeague(request));
        verify(leagueRepository, never()).save(any(League.class));
        verify(footballTeamRepository, never()).saveAll(anyList());
    }

    @Test
    void testCreateLeagueWithDuplicateTeamIdsThrowsException() {
        LeagueCreateRequest request = LeagueCreateRequest.builder()
                .name("Premier League")
                .baseTeamIdList(Arrays.asList(1L, 2L, 3L, 4L, 5L, 1L))
                .build();

        Assertions.assertThrows(ApiRequestException.class, () -> leagueService.createLeague(request));
        verify(leagueRepository, never()).save(any(League.class));
        verify(footballTeamRepository, never()).saveAll(anyList());
    }
}
