package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.Dto.BaseTeamDTO;
import com.example.FootballManager_back_end.Entity.BaseTeam;
import com.example.FootballManager_back_end.Repository.BaseTeamRepository;
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

class BaseTeamServiceTest {
    @InjectMocks
    private BaseTeamService baseTeamService;

    @Mock
    private BaseTeamRepository baseTeamRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBaseTeams() {
        BaseTeam baseTeam1 =
                new BaseTeam(1L, "Torpedo", "TOR", "SharkPool", 123456);
        BaseTeam baseTeam2 =
                new BaseTeam(2L, "CSKA", "CSKA", "BulgarianArmy", 12345);

        List<BaseTeam> baseTeamList = Arrays.asList(baseTeam1, baseTeam2);

        BaseTeamDTO baseTeamDTO1 =
                new BaseTeamDTO(1L, "Torpedo", "TOR", "SharkPool", 123456);
        BaseTeamDTO baseTeamDTO2 =
                new BaseTeamDTO(2L, "CSKA", "CSKA", "BulgarianArmy", 12345);

        List<BaseTeamDTO> expectedBaseTeamDTOList = Arrays.asList(baseTeamDTO1, baseTeamDTO2);

        when(baseTeamRepository.findAll()).thenReturn(baseTeamList);
        when(modelMapper.map(baseTeam1, BaseTeamDTO.class)).thenReturn(baseTeamDTO1);
        when(modelMapper.map(baseTeam2, BaseTeamDTO.class)).thenReturn((baseTeamDTO2));

        List<BaseTeamDTO> resultBaseTeamDTOList = baseTeamService.getAllBaseTeams();

        verify(baseTeamRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(baseTeam1, BaseTeamDTO.class);
        verify(modelMapper, times(1)).map(baseTeam2, BaseTeamDTO.class);
        Assertions.assertEquals(expectedBaseTeamDTOList, resultBaseTeamDTOList);
    }

    @Test
    void testGetAllBaseTeamsWithNoBaseTeams() {
        List<BaseTeam> baseTeamList = new ArrayList<>();
        List<BaseTeamDTO> expectedBaseTeamList = new ArrayList<>();

        when(baseTeamRepository.findAll()).thenReturn(baseTeamList);

        List<BaseTeamDTO> resultBaseTeamList = baseTeamService.getAllBaseTeams();

        verify(baseTeamRepository, times(1)).findAll();
        Assertions.assertEquals(expectedBaseTeamList,resultBaseTeamList);
    }

    @Test
    void testGetBaseTeamByIdWithExistingId() throws Exception {
        Long baseTeamId = 1L;
        BaseTeam existingBaseTeam =
                new BaseTeam(baseTeamId, "Torpedo", "TOR", "SharkPool", 123456);
        BaseTeamDTO expectedBaseTeamDTO1 =
                new BaseTeamDTO(baseTeamId, "Torpedo", "TOR", "SharkPool", 123456);

        when(baseTeamRepository.findById(baseTeamId)).thenReturn(Optional.of(existingBaseTeam));
        when(modelMapper.map(existingBaseTeam, BaseTeamDTO.class)).thenReturn(expectedBaseTeamDTO1);

        BaseTeamDTO result = baseTeamService.getBaseTeamById(baseTeamId);

        verify(baseTeamRepository, times(1)).findById(baseTeamId);
        verify(modelMapper, times(1)).map(existingBaseTeam, BaseTeamDTO.class);
        Assertions.assertEquals(expectedBaseTeamDTO1, result);
    }

    @Test
    void testGetBaseTeamByIdWithNonExistingIdThenThrowsException() {
        Long baseTeamId = 1L;
        when(baseTeamRepository.findById(baseTeamId)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> baseTeamService.getBaseTeamById(baseTeamId));
        verify(baseTeamRepository, times(1)).findById(baseTeamId);
        verify(modelMapper, never()).map(any(), eq(BaseTeamDTO.class));
    }

    @Test
    void testCreateBaseTeam() {
        BaseTeamDTO newBaseTeamDTO =
                new BaseTeamDTO(null, "New Team", "NTM", "New Stadium", 50000);
        BaseTeam baseTeam =
                new BaseTeam(1L, "New Team", "NTM", "New Stadium", 50000);
        BaseTeamDTO savedBaseTeamDTO =
                new BaseTeamDTO(1L, "New Team", "NTM", "New Stadium", 50000);

        when(modelMapper.map(newBaseTeamDTO, BaseTeam.class)).thenReturn(baseTeam);
        when(baseTeamRepository.save(baseTeam)).thenReturn(baseTeam);
        when(modelMapper.map(baseTeam, BaseTeamDTO.class)).thenReturn(savedBaseTeamDTO);

        BaseTeamDTO result = baseTeamService.createBaseTeam(newBaseTeamDTO);

        verify(baseTeamRepository, times(1)).save(baseTeam);
        verify(modelMapper, times(1)).map(newBaseTeamDTO, BaseTeam.class);
        verify(modelMapper, times(1)).map(baseTeam, BaseTeamDTO.class);
        Assertions.assertEquals(savedBaseTeamDTO, result);
    }

    @Test
    void testUpdateBaseTeam() throws Exception {
        Long baseTeamId = 1L;
        BaseTeam existingBaseTeam =
                new BaseTeam(baseTeamId, "Old Team", "OTM", "Old Stadium", 100000);
        BaseTeamDTO newBaseTeamDTO =
                new BaseTeamDTO(baseTeamId, "Updated Team", "UPT", "Updated Stadium", 200000);
        BaseTeam updatedBaseTeam =
                new BaseTeam(baseTeamId, "Updated Team", "UPT", "Updated Stadium", 200000);
        BaseTeamDTO expectedBaseTeamDTO =
                new BaseTeamDTO(baseTeamId, "Updated Team", "UPT", "Updated Stadium", 200000);

        when(baseTeamRepository.findById(baseTeamId)).thenReturn(Optional.of(existingBaseTeam));
        when(baseTeamRepository.save(existingBaseTeam)).thenReturn(updatedBaseTeam);
        when(modelMapper.map(updatedBaseTeam, BaseTeamDTO.class)).thenReturn(expectedBaseTeamDTO);

        BaseTeamDTO result = baseTeamService.updateBaseTeam(baseTeamId, newBaseTeamDTO);

        verify(baseTeamRepository, times(1)).findById(baseTeamId);
        verify(baseTeamRepository, times(1)).save(existingBaseTeam);
        verify(modelMapper, times(1)).map(updatedBaseTeam, BaseTeamDTO.class);
        Assertions.assertEquals(expectedBaseTeamDTO, result);
    }

    @Test
    void testUpdateBaseTeamWithNonExistingIdThrowsException() {
        Long baseTeamId = 1L;
        BaseTeamDTO newBaseTeamDTO =
                new BaseTeamDTO(baseTeamId, "Updated Team", "UPT", "Updated Stadium", 200000);

        when(baseTeamRepository.findById(baseTeamId)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> baseTeamService.updateBaseTeam(baseTeamId, newBaseTeamDTO));
        verify(baseTeamRepository, times(1)).findById(baseTeamId);
        verify(baseTeamRepository, never()).save(any(BaseTeam.class));
        verify(modelMapper, never()).map(any(), eq(BaseTeamDTO.class));
    }

    @Test
    void testDeleteBaseTeamById() throws Exception {
        Long baseTeamId = 1L;
        BaseTeam baseTeam =
                new BaseTeam(baseTeamId, "Team to Delete", "TTD", "Delete Stadium", 50000);

        when(baseTeamRepository.findById(baseTeamId)).thenReturn(Optional.of(baseTeam));

        String result = baseTeamService.deleteBaseTeamById(baseTeamId);

        verify(baseTeamRepository, times(1)).findById(baseTeamId);
        verify(baseTeamRepository, times(1)).delete(baseTeam);
        Assertions.assertEquals("Base team with id " + baseTeamId + " deleted successfully.", result);
    }

    @Test
    void testDeleteBaseTeamByIdWithNonExistingIdThrowsException() {
        Long baseTeamId = 1L;

        when(baseTeamRepository.findById(baseTeamId)).thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> baseTeamService.deleteBaseTeamById(baseTeamId));
        verify(baseTeamRepository, times(1)).findById(baseTeamId);
        verify(baseTeamRepository, never()).delete(any(BaseTeam.class));
    }
}