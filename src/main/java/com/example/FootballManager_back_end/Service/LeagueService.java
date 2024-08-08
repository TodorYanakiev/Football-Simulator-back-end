package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.BaseTeamDTO;
import com.example.FootballManager_back_end.DTO.Request.LeagueCreateRequest;
import com.example.FootballManager_back_end.Entity.FootballTeam;
import com.example.FootballManager_back_end.Entity.League;
import com.example.FootballManager_back_end.Enum.Status;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.FootballTeamRepository;
import com.example.FootballManager_back_end.Repository.LeagueRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final FootballTeamRepository footballTeamRepository;
    private final ModelMapper modelMapper;
    private final BaseTeamService baseTeamService;

    public String createLeague(LeagueCreateRequest request) {
        checkValidationsForLeague(request);
        League league = new League();
        league.setName(request.getName());
        league.setLeagueStatus(Status.NOT_STARTED);
        leagueRepository.save(league);
        List<FootballTeam> footballTeamList = createFootballTeamByBaseTeamIds(request.getBaseTeamIdList(), league);
        footballTeamRepository.saveAll(footballTeamList);

        return "League " + league.getName() + " is created.";
    }

    private void checkValidationsForLeague(LeagueCreateRequest request) {
        if (request.getName().length() < 3)
            throw new ApiRequestException("Name must be at least 3 symbols long.");
        if (request.getBaseTeamIdList().size() < 6)
            throw new ApiRequestException("League must have at least six teams.");
        if (request.getBaseTeamIdList().size() % 2 == 1)
            throw new ApiRequestException("League must have even number of teams.");

        List<Long> baseTeamIdList = request.getBaseTeamIdList();
        int size = baseTeamIdList.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (baseTeamIdList.get(i).equals(baseTeamIdList.get(j))) {
                    throw new ApiRequestException("League must have unique teams. BaseTeam with id: "
                            + baseTeamIdList.get(i) + " is found more than 1 time.");
                }
            }
        }
    }

    private List<FootballTeam> createFootballTeamByBaseTeamIds(List<Long> baseTeamIdList, League league) {
        List<BaseTeamDTO> checkedBaseTeamDTOList = new ArrayList<>();
        for (Long baseTeamId : baseTeamIdList) {
            checkedBaseTeamDTOList.add(baseTeamService.getBaseTeamById(baseTeamId));
        }
        List<FootballTeam> footballTeamList = new ArrayList<>();
        for(BaseTeamDTO baseTeamDTO : checkedBaseTeamDTOList) {
            FootballTeam footballTeam = new FootballTeam();
            footballTeam.setBaseTeam(baseTeamService.baseTeamDTOToBaseTeam(baseTeamDTO));
            footballTeam.setBudget(baseTeamDTO.getStartBudget());
            footballTeam.setLeague(league);
            footballTeamList.add(footballTeam);
        }
        return footballTeamList;
    }
}
