package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.User;

public interface UserServices {
    UserDTO getUserDTOById(Long id) throws UserExceptions;
    User getUserById(Long id) throws UserExceptions;
    void createNewUser (NewUser newUser);
    User saveUser(User user);
    UserDTO updateUserById(UpdateUser upUser, Long id) throws UserExceptions;
    void deleteUserById(Long id);
    boolean existsById(Long id);
}
