package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.dtos.get_with_content.GetUserDTO;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.UserModel;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserServices {
    GetUserDTO getUserDTOById(Long id) throws UserExceptions;
    UserModel getUserById(Long id) throws UserExceptions;
    List<UserModel> getUsers();
    void createNewUser (NewUser newUser);
    UserModel saveUser(UserModel userModel);
    UserDTO updateUserById(UpdateUser upUser, Long id) throws UserExceptions;
    void deleteUserById(Long id);
    boolean existsById(Long id);
}
