package com.mindhub.todolist.controllers.user_controllers;

import com.mindhub.todolist.config.CustomUserDetailsService;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.dtos.get_with_content.GetUserDTO;
import com.mindhub.todolist.exceptions.TaskExceptions;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/")
    public ResponseEntity<?> getUserById() throws UserExceptions {
        try {
            Long id = customUserDetailsService.getAuthUserId();
            GetUserDTO userDTO = userServices.getUserDTOById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (UserExceptions e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUserById(@RequestBody UpdateUser updateUser) throws UserExceptions {
        try {
            Long id = customUserDetailsService.getAuthUserId();
            if (updateUser.name().isBlank()) {
                return new ResponseEntity<>("El nombre no puede estar en blanco", HttpStatus.BAD_REQUEST);
            }
            UserDTO updatedUser = userServices.updateUserById(updateUser, id);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserExceptions e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/")
    public  ResponseEntity<?> deleteUserById() throws UserExceptions {
        Long id = customUserDetailsService.getAuthUserId();
        userServices.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
