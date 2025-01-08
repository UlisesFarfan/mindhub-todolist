package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.User;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO getUserDTOById(Long id) throws UserExceptions {
        return new UserDTO(getUserById(id));
    }

    @Override
    public User getUserById(Long id) throws UserExceptions {
        return userRepository.findById(id).orElseThrow( () -> new UserExceptions("No se encontro el usuario"));
    }

    @Override
    public void createNewUser(NewUser newUser) {
        User user = new User(newUser.name(), newUser.password(),newUser.email());
        User savedUser = saveUser(user);
        System.out.println(savedUser);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDTO updateUserById(UpdateUser upUser, Long id) throws UserExceptions {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions("No se encontro el usuario: " + id));
        user.setName(upUser.name());
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
