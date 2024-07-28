package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.BaseFootballPlayerDTO;
import com.example.FootballManager_back_end.Entity.BaseFootballPlayer;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.BaseFootballPlayerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BaseFootballPlayerService {
    private final BaseFootballPlayerRepository baseFootballPlayerRepository;
    private final ModelMapper modelMapper;

    public BaseFootballPlayerDTO baseFootballPlayerToBaseFootballPlayerDTO(BaseFootballPlayer baseFootballPlayer){
        return modelMapper.map(baseFootballPlayer, BaseFootballPlayerDTO.class);
    }
    public BaseFootballPlayer baseFootballPlayerDTOToBaseFootballPlayer(BaseFootballPlayerDTO baseFootballPlayerDTO){
        return modelMapper.map(baseFootballPlayerDTO, BaseFootballPlayer.class);
    }

    public List<BaseFootballPlayerDTO> getAllBaseFootballPlayers() {
        List<BaseFootballPlayer> players = baseFootballPlayerRepository.findAll();
        return players
                .stream()
                .map(this::baseFootballPlayerToBaseFootballPlayerDTO)
                .toList();
    }

    public BaseFootballPlayerDTO getBaseFootballPlayerById(Long id) {
        Optional<BaseFootballPlayer> player = baseFootballPlayerRepository.findById(id);
        if (player.isEmpty()) {
            throw new ApiRequestException("Player with id " + id + " not found!");
        }
        return baseFootballPlayerToBaseFootballPlayerDTO(player.get());
    }

    public BaseFootballPlayerDTO createBaseFootballPlayer(BaseFootballPlayerDTO baseFootballPlayerDTO) {
        BaseFootballPlayer baseFootballPlayer = baseFootballPlayerDTOToBaseFootballPlayer(baseFootballPlayerDTO);
        baseFootballPlayerRepository.save(baseFootballPlayer);
        return baseFootballPlayerToBaseFootballPlayerDTO(baseFootballPlayer);
    }

    public BaseFootballPlayerDTO updateBaseFootballPlayer(Long id, BaseFootballPlayerDTO playerDetails) {
        Optional<BaseFootballPlayer> playerOptional = baseFootballPlayerRepository.findById(id);
        if (playerOptional.isEmpty()) {
            throw new ApiRequestException("Player with id " + id + " not found!");
        }

        BaseFootballPlayer player = playerOptional.get();
        player.setFirstName(playerDetails.getFirstName());
        player.setLastName(playerDetails.getLastName());
        player.setNationality(playerDetails.getNationality());
        player.setAge(playerDetails.getAge());
        player.setShirtNumber(playerDetails.getShirtNumber());
        player.setPosition(playerDetails.getPosition());
        player.setStartDefending(playerDetails.getStartDefending());
        player.setStartSpeed(playerDetails.getStartSpeed());
        player.setStartDribble(playerDetails.getStartDribble());
        player.setStartScoring(playerDetails.getStartScoring());
        player.setStartPassing(playerDetails.getStartPassing());
        player.setStartStamina(playerDetails.getStartStamina());
        player.setStartPositioning(playerDetails.getStartPositioning());
        player.setStartGoalkeeping(playerDetails.getStartGoalkeeping());

        BaseFootballPlayer updatedPlayer = baseFootballPlayerRepository.save(player);
        return baseFootballPlayerToBaseFootballPlayerDTO(updatedPlayer);
    }

    public String deleteBaseFootballPlayer(Long id) {
        Optional<BaseFootballPlayer> playerOptional = baseFootballPlayerRepository.findById(id);
        if (playerOptional.isEmpty()) {
            throw new ApiRequestException("Player with id " + id + " not found!");
        }
        baseFootballPlayerRepository.delete(playerOptional.get());
        return "Player with id " + id + " deleted successfully.";
    }
}
