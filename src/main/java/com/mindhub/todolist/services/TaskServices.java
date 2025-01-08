package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.*;
import com.mindhub.todolist.exceptions.TaskExceptions;
import com.mindhub.todolist.models.Task;

import java.util.List;

public interface TaskServices {
    TaskDTO getTaskDTOById(Long id) throws TaskExceptions;
    Task getTaskById(Long id) throws TaskExceptions;
    List<Task> getTasks();
    TaskDTO createNewTask (NewTask newTask);
    Task saveTask(Task task);
    TaskDTO updateTaskById(UpdateTask updateTask, Long id) throws TaskExceptions;
    void deleteTaskById(Long id);
    boolean existsById(Long id);
}
