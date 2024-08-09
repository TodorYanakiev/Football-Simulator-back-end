package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.FootballPlayerDTO;
import com.example.FootballManager_back_end.Entity.BaseFootballPlayer;
import com.example.FootballManager_back_end.Entity.FootballPlayer;
import com.example.FootballManager_back_end.Entity.FootballTeam;
import com.example.FootballManager_back_end.Entity.League;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.BaseFootballPlayerRepository;
import com.example.FootballManager_back_end.Repository.FootballPlayerRepository;
import com.example.FootballManager_back_end.Repository.FootballTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FootballTeamServiceTest {

    @InjectMocks
    private FootballTeamService footballTeamService;

    @Mock
    private FootballTeamRepository footballTeamRepository;

    @Mock
    private FootballPlayerRepository footballPlayerRepository;

    @Mock
    private BaseFootballPlayerRepository baseFootballPlayerRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPlayersToFootballTeamSuccessfully() {
        Long teamId = 1L;
        Long leagueId = 2L;
        List<Long> basePlayerIds = Arrays.asList(1L, 2L);

        FootballTeam footballTeam = new FootballTeam();
        League league = new League();
        league.setId(leagueId);
        footballTeam.setId(teamId);
        footballTeam.setLeague(league);

        BaseFootballPlayer basePlayer1 = new BaseFootballPlayer();
        basePlayer1.setId(1L);
        BaseFootballPlayer basePlayer2 = new BaseFootballPlayer();
        basePlayer2.setId(2L);

        FootballPlayer footballPlayer1 = new FootballPlayer();
        footballPlayer1.setBaseFootballPlayer(basePlayer1);
        FootballPlayer footballPlayer2 = new FootballPlayer();
        footballPlayer2.setBaseFootballPlayer(basePlayer2);

        FootballPlayerDTO footballPlayerDTO1 = new FootballPlayerDTO();
        FootballPlayerDTO footballPlayerDTO2 = new FootballPlayerDTO();

        when(footballTeamRepository.findById(teamId)).thenReturn(Optional.of(footballTeam));
        when(baseFootballPlayerRepository.findById(1L)).thenReturn(Optional.of(basePlayer1));
        when(baseFootballPlayerRepository.findById(2L)).thenReturn(Optional.of(basePlayer2));
        when(footballPlayerRepository.findByFootballTeam_LeagueId(leagueId)).thenReturn(new ArrayList<>());
        when(baseFootballPlayerRepository.findAllById(basePlayerIds)).thenReturn(Arrays.asList(basePlayer1, basePlayer2));
        when(footballPlayerRepository.save(any(FootballPlayer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(modelMapper.map(any(FootballPlayer.class), eq(FootballPlayerDTO.class)))
                .thenReturn(footballPlayerDTO1)
                .thenReturn(footballPlayerDTO2);

        List<FootballPlayerDTO> result = footballTeamService.addPlayersToFootballTeam(teamId, basePlayerIds);

        assertEquals(2, result.size());
        verify(footballTeamRepository, times(2)).findById(teamId);
        verify(baseFootballPlayerRepository, times(1)).findById(1L);
        verify(footballPlayerRepository, times(1)).findByFootballTeam_LeagueId(leagueId);
        verify(footballPlayerRepository, times(2)).save(any(FootballPlayer.class));
        verify(modelMapper, times(2)).map(any(FootballPlayer.class), eq(FootballPlayerDTO.class));
    }

    @Test
    void testAddPlayersToFootballTeamWithNonExistingTeamThenThrowsException() {
        Long teamId = 1L;
        List<Long> basePlayerIds = Arrays.asList(1L, 2L);

        when(footballTeamRepository.findById(teamId)).thenReturn(Optional.empty());

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> footballTeamService.addPlayersToFootballTeam(teamId, basePlayerIds));

        assertEquals("Football team with id " + teamId + " does not exist.", exception.getMessage());
        verify(footballTeamRepository, times(1)).findById(teamId);
        verify(baseFootballPlayerRepository, never()).findById(anyLong());
        verify(footballPlayerRepository, never()).findByFootballTeam_LeagueId(anyLong());
    }

    @Test
    void testAddPlayersToFootballTeamWithNonExistingBasePlayerThenThrowsException() {
        Long teamId = 1L;
        List<Long> basePlayerIds = Arrays.asList(1L, 2L);
        FootballTeam footballTeam = new FootballTeam();

        when(footballTeamRepository.findById(teamId)).thenReturn(Optional.of(footballTeam));
        when(baseFootballPlayerRepository.findById(1L)).thenReturn(Optional.empty());

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> footballTeamService.addPlayersToFootballTeam(teamId, basePlayerIds));

        assertEquals("Base football player with id 1 does not exist.", exception.getMessage());
        verify(footballTeamRepository, times(1)).findById(teamId);
        verify(baseFootballPlayerRepository, times(1)).findById(1L);
        verify(baseFootballPlayerRepository, never()).findById(2L);
    }

    @Test
    void testAddPlayersToFootballTeamWithDuplicateBasePlayerIdsThenThrowsException() {
        Long teamId = 1L;
        List<Long> basePlayerIds = Arrays.asList(1L, 1L);
        FootballTeam footballTeam = new FootballTeam();

        when(footballTeamRepository.findById(teamId)).thenReturn(Optional.of(footballTeam));
        when(baseFootballPlayerRepository.findById(1L)).thenReturn(Optional.of(new BaseFootballPlayer()));

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> footballTeamService.addPlayersToFootballTeam(teamId, basePlayerIds));

        assertEquals("Team must have unique players. Base player with id: 1 is found more than 1 time.", exception.getMessage());
        verify(footballTeamRepository, times(1)).findById(teamId);
        verify(baseFootballPlayerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddPlayersToFootballTeamWithPlayerAlreadyUsedInOtherTeamThenThrowsException() {
        Long teamId = 1L;
        Long leagueId = 2L;
        List<Long> basePlayerIds = Arrays.asList(1L, 2L);

        FootballTeam footballTeam = new FootballTeam();
        League league = new League();
        league.setId(leagueId);
        footballTeam.setLeague(league);

        FootballPlayer existingPlayer = new FootballPlayer();
        BaseFootballPlayer basePlayer = new BaseFootballPlayer();
        basePlayer.setId(1L);
        existingPlayer.setBaseFootballPlayer(basePlayer);

        when(footballTeamRepository.findById(teamId)).thenReturn(Optional.of(footballTeam));
        when(baseFootballPlayerRepository.findById(1L)).thenReturn(Optional.of(new BaseFootballPlayer()));
        when(baseFootballPlayerRepository.findById(2L)).thenReturn(Optional.of(new BaseFootballPlayer()));
        when(footballPlayerRepository.findByFootballTeam_LeagueId(leagueId)).thenReturn(List.of(existingPlayer));

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> footballTeamService.addPlayersToFootballTeam(teamId, basePlayerIds));

        assertEquals("Base player with id 1 is already used in the league.", exception.getMessage());
        verify(footballTeamRepository, times(2)).findById(teamId);
        verify(baseFootballPlayerRepository, times(1)).findById(1L);
        verify(footballPlayerRepository, times(1)).findByFootballTeam_LeagueId(leagueId);
    }
}
