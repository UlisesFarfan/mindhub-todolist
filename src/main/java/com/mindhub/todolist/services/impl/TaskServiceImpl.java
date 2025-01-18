package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UpdateTask;
import com.mindhub.todolist.exceptions.TaskExceptions;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskServices {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Override
    public TaskDTO getTaskDTOById(Long id) throws TaskExceptions {
        try {
            return new TaskDTO(getTaskById(id));
        } catch (TaskExceptions e) {
            throw new TaskExceptions("Error al obtener la tarea por ID: " + e.getMessage());
        }
    }

    @Override
    public Task getTaskById(Long id) throws TaskExceptions {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskExceptions("No se encontró la tarea con ID: " + id));
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public TaskDTO createNewTask(NewTask newTask) {
        Task task = new Task(newTask.title(), newTask.description(), newTask.status(), newTask.userModel());
        Task savedTask = saveTask(task);
        return new TaskDTO(savedTask);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public TaskDTO updateTaskById(UpdateTask updateTask, Long id) throws TaskExceptions {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskExceptions("No se ha encontrado la tarea: " + id));
        task.setTitle(updateTask.title());
        task.setDescription(updateTask.description());
        task.setStatus(updateTask.status());
        Task updatedTask = taskRepository.save(task);
        return new TaskDTO(updatedTask);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
