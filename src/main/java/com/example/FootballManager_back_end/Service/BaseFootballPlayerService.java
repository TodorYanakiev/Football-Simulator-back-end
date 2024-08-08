package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.BaseFootballPlayerDTO;
import com.example.FootballManager_back_end.Entity.BaseFootballPlayer;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.Repository.BaseFootballPlayerRepository;
import com.example.FootballManager_back_end.Utils.ValidationUtils;
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

    private static final String PLAYER_NOT_FOUND_MESSAGE = "Base player with id %d not found!";

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
            throw new ApiRequestException(String.format(PLAYER_NOT_FOUND_MESSAGE, id));
        }
        return baseFootballPlayerToBaseFootballPlayerDTO(player.get());
    }

    public BaseFootballPlayerDTO createBaseFootballPlayer(BaseFootballPlayerDTO baseFootballPlayerDTO) {
        checkValidations(baseFootballPlayerDTO);
        BaseFootballPlayer baseFootballPlayer = baseFootballPlayerDTOToBaseFootballPlayer(baseFootballPlayerDTO);
        baseFootballPlayerRepository.save(baseFootballPlayer);
        return baseFootballPlayerToBaseFootballPlayerDTO(baseFootballPlayer);
    }

    public BaseFootballPlayerDTO updateBaseFootballPlayer(Long id, BaseFootballPlayerDTO playerDetails) {
        Optional<BaseFootballPlayer> playerOptional = baseFootballPlayerRepository.findById(id);
        if (playerOptional.isEmpty()) {
            throw new ApiRequestException(String.format(PLAYER_NOT_FOUND_MESSAGE, id));
        }

        checkValidations(playerDetails);
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
            throw new ApiRequestException(String.format(PLAYER_NOT_FOUND_MESSAGE, id));
        }
        baseFootballPlayerRepository.delete(playerOptional.get());
        return "Base player with id " + id + " deleted successfully.";
    }

    protected void checkValidations(BaseFootballPlayerDTO newBaseFootballPlayer) {
        ValidationUtils.validateStringField(newBaseFootballPlayer.getFirstName(), "First name", 2);
        ValidationUtils.validateStringField(newBaseFootballPlayer.getLastName(), "Last name", 2);
        ValidationUtils.validateStringField(newBaseFootballPlayer.getNationality(), "Nationality", 2);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getAge(), "Age", 15, null);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getShirtNumber(), "Shirt number", 1, 99);
        if (newBaseFootballPlayer.getPosition() == null)
            throw new ApiRequestException("Position cannot be null.");
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartDefending(), "Defending", 1, 99);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartSpeed(), "Speed", 1, 99);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartDribble(), "Dribble", 1, 99);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartScoring(), "Scoring", 1, 99);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartPassing(), "Passing", 1, 99);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartStamina(), "Stamina", 1, 99);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartPositioning(), "Positioning", 1, 99);
        ValidationUtils.validateIntField(newBaseFootballPlayer.getStartGoalkeeping(), "Goalkeeping", 1, 99);
    }

}
