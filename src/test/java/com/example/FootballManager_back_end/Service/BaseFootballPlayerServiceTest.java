package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.BaseFootballPlayerDTO;
import com.example.FootballManager_back_end.Entity.BaseFootballPlayer;
import com.example.FootballManager_back_end.Enum.Position;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.BaseFootballPlayerRepository;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.*;

class BaseFootballPlayerServiceTest {
    @InjectMocks
    private BaseFootballPlayerService baseFootballPlayerService;

    @Mock
    private BaseFootballPlayerRepository baseFootballPlayerRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBaseFootballPlayers() {
        BaseFootballPlayer player1 = new BaseFootballPlayer(1L, "John", "Doe", "USA", (byte) 25, (byte) 10, Position.LCB,
                (byte) 70, (byte) 85, (byte) 90, (byte) 75, (byte) 80, (byte) 65, (byte) 68, (byte) 60);
        BaseFootballPlayer player2 = new BaseFootballPlayer();
        player2.setId(2L);
        player2.setFirstName("Jane");
        player2.setLastName("Doe");
        player2.setNationality("CAN");
        player2.setAge((byte) 22);
        player2.setShirtNumber((byte) 15);
        player2.setPosition(Position.LCF);
        player2.setStartDefending((byte) 60);
        player2.setStartSpeed((byte) 80);
        player2.setStartDribble((byte) 85);
        player2.setStartScoring((byte) 70);
        player2.setStartPassing((byte) 75);
        player2.setStartStamina((byte) 70);
        player2.setStartPositioning((byte) 65);
        player2.setStartGoalkeeping((byte) 50);

        List<BaseFootballPlayer> playerList = Arrays.asList(player1, player2);

        BaseFootballPlayerDTO playerDTO1 = new BaseFootballPlayerDTO(1L, "John", "Doe", "USA", (byte) 25, (byte) 10, Position.LCB,
                (byte) 70, (byte) 85, (byte) 90, (byte) 75, (byte) 80, (byte) 65, (byte) 68, (byte) 60);
        BaseFootballPlayerDTO playerDTO2 = new BaseFootballPlayerDTO(2L, "Jane", "Doe", "CAN", (byte) 22, (byte) 15, Position.LCF,
                (byte) 60, (byte) 80, (byte) 85, (byte) 70, (byte) 75, (byte) 70, (byte) 65, (byte) 50);

        List<BaseFootballPlayerDTO> expectedPlayerDTOList = Arrays.asList(playerDTO1, playerDTO2);

        when(baseFootballPlayerRepository.findAll()).thenReturn(playerList);
        when(baseFootballPlayerService.baseFootballPlayerToBaseFootballPlayerDTO(player1)).thenReturn(playerDTO1);
        when(baseFootballPlayerService.baseFootballPlayerToBaseFootballPlayerDTO(player2)).thenReturn(playerDTO2);

        List<BaseFootballPlayerDTO> resultPlayerDTOList = baseFootballPlayerService.getAllBaseFootballPlayers();

        Assertions.assertEquals(expectedPlayerDTOList, resultPlayerDTOList);
        Assertions.assertEquals(expectedPlayerDTOList.get(0).getFirstName(), resultPlayerDTOList.get(0).getFirstName());
    }

    @Test
    void testGetAllBaseFootballPlayersWithNoPlayers() {
        List<BaseFootballPlayer> playerList = new ArrayList<>();
        List<BaseFootballPlayerDTO> expectedPlayerDTOList = new ArrayList<>();

        when(baseFootballPlayerRepository.findAll()).thenReturn(playerList);

        List<BaseFootballPlayerDTO> resultPlayerDTOList = baseFootballPlayerService.getAllBaseFootballPlayers();

        verify(baseFootballPlayerRepository, times(1)).findAll();
        Assertions.assertEquals(expectedPlayerDTOList, resultPlayerDTOList);
    }

    @Test
    void testGetBaseFootballPlayerByIdWithExistingId() {
        Long playerId = 1L;
        BaseFootballPlayer existingPlayer = new BaseFootballPlayer(playerId, "John", "Doe", "USA", (byte) 25, (byte) 10, Position.RM,
                (byte) 70, (byte) 85, (byte) 90, (byte) 75, (byte) 80, (byte) 65, (byte) 68, (byte) 60);
        BaseFootballPlayerDTO expectedPlayerDTO = new BaseFootballPlayerDTO(playerId, "John", "Doe", "USA", (byte) 25, (byte) 10, Position.RF,
                (byte) 70, (byte) 85, (byte) 90, (byte) 75, (byte) 80, (byte) 65, (byte) 68, (byte) 60);

        when(baseFootballPlayerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));
        when(baseFootballPlayerService.baseFootballPlayerToBaseFootballPlayerDTO(existingPlayer)).thenReturn(expectedPlayerDTO);

        BaseFootballPlayerDTO result = baseFootballPlayerService.getBaseFootballPlayerById(playerId);
        Assertions.assertEquals(expectedPlayerDTO, result);
    }

    @Test
    void testGetBaseFootballPlayerByIdWithNonExistingIdThenThrowsException() {
        Long playerId = 1L;
        when(baseFootballPlayerRepository.findById(playerId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ApiRequestException.class, () -> baseFootballPlayerService.getBaseFootballPlayerById(playerId));
    }

}
