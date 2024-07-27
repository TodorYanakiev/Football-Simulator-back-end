package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.Dto.BaseTeamDTO;
import com.example.FootballManager_back_end.Entity.BaseTeam;
import com.example.FootballManager_back_end.Repository.BaseTeamRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BaseTeamService {
    private final BaseTeamRepository baseTeamRepository;
    private final ModelMapper modelMapper;

    public BaseTeamDTO baseTeamToBaseTeamDTO(BaseTeam baseTeam) {
        return modelMapper.map(baseTeam, BaseTeamDTO.class);
    }

    public BaseTeam baseTeamDTOToBaseTeam(BaseTeamDTO baseTeamDTO) {
        return modelMapper.map(baseTeamDTO, BaseTeam.class);
    }

    public BaseTeamDTO createBaseTeam(BaseTeamDTO baseTeamDto) {
        BaseTeam baseTeam = baseTeamDTOToBaseTeam(baseTeamDto);
        baseTeamRepository.save(baseTeam);
        return baseTeamToBaseTeamDTO(baseTeam);
    }

    public List<BaseTeamDTO> getAllBaseTeams() {
        List<BaseTeam> baseTeamList = baseTeamRepository.findAll();
        return baseTeamList.stream().map(this::baseTeamToBaseTeamDTO).toList();
    }

    public BaseTeamDTO getBaseTeamById(Long id) throws Exception {
        Optional<BaseTeam> optionalBaseTeam = baseTeamRepository.findById(id);
        if (optionalBaseTeam.isEmpty()) {
            throw new Exception("Base team with id " + id + " not found.");
        }
        return baseTeamToBaseTeamDTO(optionalBaseTeam.get());
    }

    public BaseTeamDTO updateBaseTeam(Long id, BaseTeamDTO newBaseTeamDto) throws Exception {
        Optional<BaseTeam> optionalBaseTeam = baseTeamRepository.findById(id);
        if (optionalBaseTeam.isEmpty()) {
            throw new Exception("Base team with id " + id + " not found.");
        }
        BaseTeam baseTeam = optionalBaseTeam.get();
        baseTeam.setName(newBaseTeamDto.getName());
        baseTeam.setAbbreviation(newBaseTeamDto.getAbbreviation());
        baseTeam.setStadiumName(newBaseTeamDto.getStadiumName());
        baseTeam.setStartBudget(newBaseTeamDto.getStartBudget());
        BaseTeam updatedBaseTeam = baseTeamRepository.save(baseTeam);
        return baseTeamToBaseTeamDTO(updatedBaseTeam);
    }

    public String deleteBaseTeamById(Long id) throws Exception {
        Optional<BaseTeam> optionalBaseTeam = baseTeamRepository.findById(id);
        if (optionalBaseTeam.isEmpty()) {
            throw new Exception("Base team with id " + id + " not found.");
        }
        baseTeamRepository.delete(optionalBaseTeam.get());
        return "Base team with id " + id + " deleted successfully.";
    }
}
