package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.*;
import com.mindhub.todolist.exceptions.TaskExceptions;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @GetMapping("/{id}")
    private ResponseEntity<?> getTaskById(@PathVariable Long id) throws TaskExceptions {
        TaskDTO taskDTO = taskServices.getTaskDTOById(id);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<?> getTasks() {
        List<TaskDTO> tasklist = taskServices.getTasks()
                .stream()
                .map(TaskDTO::new)
                .toList();
        return new ResponseEntity<>(tasklist, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody NewTask newTask) {
        if(newTask.title().isBlank()) {
            return new ResponseEntity<>("El titulo no puede estar en blanco", HttpStatus.BAD_REQUEST);
        }
        TaskDTO savedTask = taskServices.createNewTask(newTask);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@RequestBody UpdateTask updateTask, @PathVariable Long id) throws TaskExceptions {
        if (updateTask.title().isBlank()) {
            return new ResponseEntity<>("El titulo no puede estar en blanco", HttpStatus.BAD_REQUEST);
        }
        TaskDTO updatedTask = taskServices.updateTaskById(updateTask, id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        taskServices.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
