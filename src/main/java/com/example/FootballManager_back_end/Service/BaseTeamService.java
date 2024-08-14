package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.BaseTeamDTO;
import com.example.FootballManager_back_end.Entity.BaseTeam;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
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

    private static final String TEAM_NOT_FOUND_MESSAGE = "Base team with id %d not found!";

    public BaseTeamDTO baseTeamToBaseTeamDTO(BaseTeam baseTeam) {
        return modelMapper.map(baseTeam, BaseTeamDTO.class);
    }

    public BaseTeam baseTeamDTOToBaseTeam(BaseTeamDTO baseTeamDTO) {
        return modelMapper.map(baseTeamDTO, BaseTeam.class);
    }

    public BaseTeamDTO createBaseTeam(BaseTeamDTO baseTeamDto) {
        checkValidations(baseTeamDto);
        BaseTeam baseTeam = baseTeamDTOToBaseTeam(baseTeamDto);
        baseTeamRepository.save(baseTeam);
        return baseTeamToBaseTeamDTO(baseTeam);
    }

    public List<BaseTeamDTO> getAllBaseTeams() {
        List<BaseTeam> baseTeamList = baseTeamRepository.findAll();
        return baseTeamList.stream().map(this::baseTeamToBaseTeamDTO).toList();
    }

    public BaseTeamDTO getBaseTeamById(Long id) {
        Optional<BaseTeam> optionalBaseTeam = baseTeamRepository.findById(id);
        if (optionalBaseTeam.isEmpty()) {
            throw new ApiRequestException(String.format(TEAM_NOT_FOUND_MESSAGE, id));
        }
        return baseTeamToBaseTeamDTO(optionalBaseTeam.get());
    }

    public BaseTeamDTO updateBaseTeamById(Long id, BaseTeamDTO newBaseTeamDto) {
        Optional<BaseTeam> optionalBaseTeam = baseTeamRepository.findById(id);
        if (optionalBaseTeam.isEmpty()) {
            throw new ApiRequestException(String.format(TEAM_NOT_FOUND_MESSAGE, id));
        }
        checkValidations(newBaseTeamDto);
        BaseTeam baseTeam = optionalBaseTeam.get();
        baseTeam.setName(newBaseTeamDto.getName());
        baseTeam.setAbbreviation(newBaseTeamDto.getAbbreviation());
        baseTeam.setStadiumName(newBaseTeamDto.getStadiumName());
        baseTeam.setStartBudget(newBaseTeamDto.getStartBudget());
        BaseTeam updatedBaseTeam = baseTeamRepository.save(baseTeam);
        return baseTeamToBaseTeamDTO(updatedBaseTeam);
    }

    private void checkValidations(BaseTeamDTO newBaseTeamDto) {
        if (newBaseTeamDto.getName() == null || newBaseTeamDto.getName().length() < 3)
            throw new ApiRequestException("Name must be at least 3 symbols long.");
        else if (newBaseTeamDto.getAbbreviation() == null || newBaseTeamDto.getAbbreviation().length() < 2
                || newBaseTeamDto.getAbbreviation().length() > 4)
            throw new ApiRequestException("Abbreviation must be between 2 and 4 symbols long.");
        else if (newBaseTeamDto.getStadiumName() == null || newBaseTeamDto.getStadiumName().length() < 3)
            throw new ApiRequestException("Stadium name must be at least 3 symbols long.");
        else if (newBaseTeamDto.getStartBudget() < 0) throw new ApiRequestException("Budget can not be negative.");
    }

    public String deleteBaseTeamById(Long id) {
        Optional<BaseTeam> optionalBaseTeam = baseTeamRepository.findById(id);
        if (optionalBaseTeam.isEmpty()) {
            throw new ApiRequestException(String.format(TEAM_NOT_FOUND_MESSAGE, id));
        }
        baseTeamRepository.delete(optionalBaseTeam.get());
        return "Base team with id " + id + " deleted successfully.";
    }
}
