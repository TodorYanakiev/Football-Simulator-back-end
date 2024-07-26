package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.Dto.BaseTeamDto;
import com.example.FootballManager_back_end.Entity.BaseTeam;
import com.example.FootballManager_back_end.Mapper.BaseTeamMapper;
import com.example.FootballManager_back_end.Repository.BaseTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BaseTeamService {
    private final BaseTeamRepository baseTeamRepository;

    public BaseTeamDto addBaseTeam(BaseTeamDto baseTeamDto) {
        BaseTeam baseTeam = BaseTeamMapper.toBaseTeam(baseTeamDto);
        baseTeamRepository.save(baseTeam);
        baseTeamDto = BaseTeamMapper.toBaseTeamDto(baseTeam);
        return baseTeamDto;
    }
}
