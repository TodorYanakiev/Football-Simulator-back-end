package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.UserDTO;
import com.example.FootballManager_back_end.user.User;
import com.example.FootballManager_back_end.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findByUsername(username);
        return userToUserDTO(user);
    }
}
