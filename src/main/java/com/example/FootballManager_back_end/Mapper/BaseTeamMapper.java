package com.example.FootballManager_back_end.Mapper;

import com.example.FootballManager_back_end.Dto.BaseTeamDTO;
import com.example.FootballManager_back_end.Entity.BaseTeam;
import org.springframework.stereotype.Component;

@Component
public class BaseTeamMapper {
    public static BaseTeam toBaseTeam(BaseTeamDTO baseTeamDto) {
        BaseTeam baseTeam = new BaseTeam();
        baseTeam.setId(baseTeamDto.getId());
        baseTeam.setName(baseTeamDto.getName());
        baseTeam.setAbbreviation(baseTeamDto.getAbbreviation());
        baseTeam.setStadiumName(baseTeamDto.getStadiumName());
        baseTeam.setStartBudget(baseTeamDto.getStartBudget());
        return baseTeam;
    }

    public static BaseTeamDTO toBaseTeamDto(BaseTeam baseTeam) {
        BaseTeamDTO baseTeamDto = new BaseTeamDTO();
        baseTeamDto.setId(baseTeam.getId());
        baseTeamDto.setName(baseTeam.getName());
        baseTeamDto.setAbbreviation(baseTeam.getAbbreviation());
        baseTeamDto.setStadiumName(baseTeam.getStadiumName());
        baseTeamDto.setStartBudget(baseTeam.getStartBudget());
        return baseTeamDto;
    }
}
