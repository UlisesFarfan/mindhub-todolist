package com.mindhub.todolist.controllers.admin_controller;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UpdateUser;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.dtos.get_with_content.GetUserDTO;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    @Autowired
    UserServices userServices;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws UserExceptions {
        try {
            GetUserDTO userDTO = userServices.getUserDTOById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (UserExceptions e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDTO> tasklist = userServices.getUsers()
                .stream()
                .map(UserDTO::new)
                .toList();
        return new ResponseEntity<>(tasklist, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@RequestBody UpdateUser updateUser, @PathVariable Long id) throws UserExceptions {
        try {
            if (updateUser.name().isBlank() || updateUser.email().isBlank()) {
                return new ResponseEntity<>("El nombre no puede estar en blanco", HttpStatus.BAD_REQUEST);
            }
            UserDTO updatedUser = userServices.updateUserById(updateUser, id);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserExceptions e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userServices.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
