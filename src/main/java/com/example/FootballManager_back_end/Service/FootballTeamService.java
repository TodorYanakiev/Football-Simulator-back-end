package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.FootballPlayerDTO;
import com.example.FootballManager_back_end.Entity.BaseFootballPlayer;
import com.example.FootballManager_back_end.Entity.FootballPlayer;
import com.example.FootballManager_back_end.Entity.FootballTeam;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.BaseFootballPlayerRepository;
import com.example.FootballManager_back_end.Repository.FootballPlayerRepository;
import com.example.FootballManager_back_end.Repository.FootballTeamRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballTeamService {
    private final FootballTeamRepository footballTeamRepository;
    private final FootballPlayerRepository footballPlayerRepository;
    private final BaseFootballPlayerRepository baseFootballPlayerRepository;
    private final ModelMapper modelMapper;

    public List<FootballPlayerDTO> addPlayersToFootballTeam(Long teamId, List<Long> basePlayerIds) {
        checkValidationsForAddingPlayers(teamId, basePlayerIds);
        FootballTeam footballTeam = footballTeamRepository.findById(teamId).get();
        checkIfPlayersAreUsedInOtherTeams(footballTeam.getLeague().getId(), basePlayerIds);
        List<FootballPlayer> addedFootballPlayers = createFootballPlayersByBasePlayers(basePlayerIds, footballTeam);
        return addedFootballPlayers.stream()
                .map(footballPlayer -> modelMapper.map(footballPlayer, FootballPlayerDTO.class))
                .toList();
    }

    private void checkValidationsForAddingPlayers(Long footballTeamId, List<Long> basePlayerIds) {
        if (footballTeamRepository.findById(footballTeamId).isEmpty())
            throw new ApiRequestException("Football team with id " + footballTeamId + " does not exist.");
        int size = basePlayerIds.size();
        for (int i = 0; i < size - 1; i++) {
            if (baseFootballPlayerRepository.findById(basePlayerIds.get(i)).isEmpty())
                throw new ApiRequestException("Base football player with id " + basePlayerIds.get(i) + " does not exist.");
            for (int j = i + 1; j < size; j++) {
                if (basePlayerIds.get(i).equals(basePlayerIds.get(j))) {
                    throw  new ApiRequestException("Team must have unique players. Base player with id: "
                            + basePlayerIds.get(i) + " is found more than 1 time.");
                }
            }
        }
    }

    private void checkIfPlayersAreUsedInOtherTeams(Long leagueId, List<Long> basePlayerIds) {
        List<FootballPlayer> usedFootballPlayers = footballPlayerRepository.findByFootballTeam_LeagueId(leagueId);
        List<Long> usedBasePlayerIds = usedFootballPlayers.stream()
                .map(footballPlayer -> footballPlayer.getBaseFootballPlayer().getId())
                .toList();
        for (Long id : basePlayerIds) {
            for (Long id2 : usedBasePlayerIds) {
                if (id.equals(id2)) throw new ApiRequestException("Base player with id " + id + " is already used in the league.");
            }
        }
    }

    private List<FootballPlayer> createFootballPlayersByBasePlayers(List<Long> basePlayerIds, FootballTeam footballTeam) {
        List<BaseFootballPlayer> baseFootballPlayers = baseFootballPlayerRepository.findAllById(basePlayerIds);
        List<FootballPlayer> newFootballPlayers = new ArrayList<>();
        for(BaseFootballPlayer basePlayer : baseFootballPlayers) {
            FootballPlayer footballPlayer = new FootballPlayer();
            footballPlayer.setBaseFootballPlayer(basePlayer);
            footballPlayer.setAge(basePlayer.getAge());
            footballPlayer.setShirtNumber(basePlayer.getShirtNumber());
            footballPlayer.setPosition(basePlayer.getPosition());
            footballPlayer.setFootballTeam(footballTeam);
            footballPlayer.setPrice(100); //add logic when time comes
            footballPlayer.setDefending(basePlayer.getStartDefending());
            footballPlayer.setSpeed(basePlayer.getStartSpeed());
            footballPlayer.setDribble(basePlayer.getStartDribble());
            footballPlayer.setScoring(basePlayer.getStartScoring());
            footballPlayer.setPassing(basePlayer.getStartPassing());
            footballPlayer.setStamina(basePlayer.getStartStamina());
            footballPlayer.setPositioning(basePlayer.getStartPositioning());
            footballPlayer.setGoalkeeping(basePlayer.getStartGoalkeeping());
            newFootballPlayers.add(footballPlayer);
            footballPlayerRepository.save(footballPlayer);
        }
        return newFootballPlayers;
    }
}
