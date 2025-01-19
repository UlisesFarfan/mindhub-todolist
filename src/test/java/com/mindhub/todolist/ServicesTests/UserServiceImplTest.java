package com.mindhub.todolist.ServicesTests;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.dtos.get_with_content.GetUserDTO;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserModel userModel;

    @BeforeEach
    public void setup() {
        userModel = new UserModel();
        userModel.setName("Ulises Farfan");
        userModel.setEmail("ulisesfarfan@example.com");
        userModel.setPassword("encrypted_password");
    }

    @Test
    public void testGetUserById_Success() throws UserExceptions {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));

        UserModel result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Ulises Farfan", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserExceptions.class, () -> userService.getUserById(1L));

        assertEquals("No se encontro el usuario", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserDTOById_Success() throws UserExceptions {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));

        GetUserDTO result = userService.getUserDTOById(1L);

        assertNotNull(result);
        assertEquals("Ulises Farfan", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUsers_Success() {
        when(userRepository.findAll()).thenReturn(List.of(userModel));

        List<UserModel> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ulises Farfan", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testCreateNewUser_Success() {
        NewUser newUser = new NewUser("User Test", "user.test@example.com", "password");

        when(passwordEncoder.encode(newUser.password())).thenReturn("encrypted_password");
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        userService.createNewUser(newUser);

        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    public void testUpdateUserById_Success() throws UserExceptions {
        UpdateUser updateUser = new UpdateUser("User Test", "user.test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));
        when(userRepository.save(userModel)).thenReturn(userModel);

        UserDTO result = userService.updateUserById(updateUser, 1L);

        assertNotNull(result);
        assertEquals("User Test", result.getName());
        assertEquals("user.test@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(userModel);
    }

    @Test
    public void testUpdateUserById_UserNotFound() {
        UpdateUser updateUser = new UpdateUser("User Test", "user.test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserExceptions.class, () -> userService.updateUserById(updateUser, 1L));

        assertEquals("No se encontro el usuario: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteUserById_Success() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUserById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

}

