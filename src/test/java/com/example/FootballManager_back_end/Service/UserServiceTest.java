package com.example.FootballManager_back_end.Service;

import com.example.FootballManager_back_end.DTO.UserDTO;
import com.example.FootballManager_back_end.Exception.ApiRequestException;
import com.example.FootballManager_back_end.user.User;
import com.example.FootballManager_back_end.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserToUserDTO() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testUser");

        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.userToUserDTO(user);

        verify(modelMapper, times(1)).map(user, UserDTO.class);
        Assertions.assertEquals(userDTO, result);
    }

    @Test
    void testUserDTOToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testUser");

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        when(modelMapper.map(userDTO, User.class)).thenReturn(user);

        User result = userService.userDTOToUser(userDTO);

        verify(modelMapper, times(1)).map(userDTO, User.class);
        Assertions.assertEquals(user, result);
    }

    @Test
    void testFindUserByUsernameWithExistingUser() {
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.findUserByUsername(username);

        verify(userRepository, times(1)).findByUsername(username);
        verify(modelMapper, times(1)).map(user, UserDTO.class);
        Assertions.assertEquals(userDTO, result);
    }

    @Test
    void testFindUserByUsernameWithNonExistingUser() {
        String username = "nonExistingUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(ApiRequestException.class, () -> userService.findUserByUsername(username));

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindUserByEmailWithExistingUser() {
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.findUserByEmail(email);

        verify(userRepository, times(1)).findByEmail(email);
        verify(modelMapper, times(1)).map(user, UserDTO.class);
        Assertions.assertEquals(userDTO, result);
    }

    @Test
    void testFindUserByEmailWithNonExistingUser() {
        String email = "nonExisting@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(ApiRequestException.class, () -> userService.findUserByEmail(email));

        verify(userRepository, times(1)).findByEmail(email);
    }
}
