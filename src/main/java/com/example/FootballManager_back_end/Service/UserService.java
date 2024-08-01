package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.UserDTO;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.user.User;
import com.example.FootballManager_back_end.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO userToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User userDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            return userToUserDTO(user.get());
        } else {
            throw new ApiRequestException("User with username " + username + " not found.");
        }

    }

    public UserDTO findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return userToUserDTO(user.get());
        } else {
            throw new ApiRequestException("User with email " + email + " not found.");
        }
    }

    public boolean doesUserExist(User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        return userByEmail.isPresent();
    }
}
