package com.mindhub.todolist.controllers.admin_controller;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UpdateTask;
import com.mindhub.todolist.dtos.get_with_content.GetUserDTO;
import com.mindhub.todolist.exceptions.TaskExceptions;
import com.mindhub.todolist.exceptions.UserExceptions;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.services.TaskServices;
import com.mindhub.todolist.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/task")
public class AdminTaskController {

    @Autowired
    TaskServices taskServices;

    @GetMapping
    public ResponseEntity<?> getTasks() {
        List<TaskDTO> tasklist = taskServices.getTasks()
                .stream()
                .map(TaskDTO::new)
                .toList();
        return new ResponseEntity<>(tasklist, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody NewTask newTask) {
        if(newTask.title().isBlank() || newTask.description().isBlank() || newTask.userModel().getId().toString().isBlank()) {
            return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
        }
        TaskDTO savedTask = taskServices.createNewTask(newTask);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) throws TaskExceptions, UserExceptions {
        TaskDTO taskDTO = taskServices.getTaskDTOById(id);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@RequestBody UpdateTask updateTask, @PathVariable Long id) throws TaskExceptions {
        if(updateTask.title().isBlank() || updateTask.description().isBlank()) {
            return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
        }
        TaskDTO updatedTask = taskServices.updateTaskById(updateTask, id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        taskServices.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
