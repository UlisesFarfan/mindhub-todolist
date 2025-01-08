package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws UserExceptions {
        try {
            UserDTO userDTO = userServices.getUserDTOById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid UUID format", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody NewUser newUser) {
        if(newUser.email().isBlank()) {
            return new ResponseEntity<>("El email no puede estar en blanco", HttpStatus.BAD_REQUEST);
        }
        userServices.createNewUser(newUser);
        return new ResponseEntity<>("Se creo la persona", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@RequestBody UpdateUser updateUser, @PathVariable Long id) throws UserExceptions {
        if (updateUser.name().isBlank()) {
            return new ResponseEntity<>("El nombre no puede estar en blanco", HttpStatus.BAD_REQUEST);
        }
        UserDTO updatedUser = userServices.updateUserById(updateUser, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteUserById(@PathVariable Long id) throws UserExceptions {
        userServices.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
