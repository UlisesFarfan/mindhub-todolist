package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.dtos.get_with_content.GetUserDTO;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public GetUserDTO getUserDTOById(Long id) throws UserExceptions {
        return new GetUserDTO(getUserById(id));
    }

    @Override
    public UserModel getUserById(Long id) throws UserExceptions {
        return userRepository.findById(id).orElseThrow( () -> new UserExceptions("No se encontro el usuario"));
    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createNewUser(NewUser newUser) {
        UserModel userModel = new UserModel(newUser.name(), passwordEncoder.encode(newUser.password()),newUser.email());
        UserModel savedUserModel = saveUser(userModel);
        System.out.println(savedUserModel);
    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public UserDTO updateUserById(UpdateUser upUser, Long id) throws UserExceptions {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new UserExceptions("No se encontro el usuario: " + id));
        userModel.setName(upUser.name());
        userModel.setEmail(upUser.email());
        userModel = userRepository.save(userModel);
        return new UserDTO(userModel);
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
