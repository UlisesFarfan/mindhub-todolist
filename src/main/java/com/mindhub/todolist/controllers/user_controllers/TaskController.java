package com.mindhub.todolist.controllers.user_controllers;

import com.mindhub.todolist.config.CustomUserDetailsService;
import com.mindhub.todolist.dtos.*;
import com.mindhub.todolist.exceptions.TaskExceptions;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) throws TaskExceptions {
        Long user_id = customUserDetailsService.getAuthUserId();
        Task task = taskServices.getTaskById(id);
        if (!task.getUser().getId().equals(user_id)){
            throw new TaskExceptions("Invalid User");
        }
        TaskDTO taskDTO = taskServices.getTaskDTOById(id);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody NewTask newTask) throws TaskExceptions {
        Long user_id = customUserDetailsService.getAuthUserId();
        if (!newTask.userModel().getId().equals(user_id)){
            throw new TaskExceptions("Invalid User");
        }
        if(newTask.title().isBlank() || newTask.description().isBlank() || newTask.userModel().getId().toString().isBlank()) {
            return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
        }
        TaskDTO savedTask = taskServices.createNewTask(newTask);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@RequestBody UpdateTask updateTask, @PathVariable Long id) throws TaskExceptions, UserExceptions {
        Long user_id = customUserDetailsService.getAuthUserId();
        Task task = taskServices.getTaskById(id);
        if (!task.getUser().getId().equals(user_id)){
            throw new TaskExceptions("Invalid User");
        }
        if (updateTask.title().isBlank()) {
            return new ResponseEntity<>("El titulo no puede estar en blanco", HttpStatus.BAD_REQUEST);
        }
        TaskDTO updatedTask = taskServices.updateTaskById(updateTask, id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) throws TaskExceptions {
            Long user_id = customUserDetailsService.getAuthUserId();
            Task task = taskServices.getTaskById(id);
            if (!task.getUser().getId().equals(user_id)){
                throw new TaskExceptions("Invalid User");
            }
            taskServices.deleteTaskById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
