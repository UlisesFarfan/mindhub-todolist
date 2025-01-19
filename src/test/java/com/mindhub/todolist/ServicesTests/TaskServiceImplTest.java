package com.mindhub.todolist.ServicesTests;

import com.mindhub.todolist.dtos.NewTask;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UpdateTask;
import com.mindhub.todolist.exceptions.TaskExceptions;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.Task.TaskStatus;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private NewTask newTask;
    private UpdateTask updateTask;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        UserModel user = new UserModel("Ulises Farfan", "password123", "ulisesfarfan@example.com");

        task = new Task("Task 1", "Description 1", TaskStatus.PENDING, user);

        newTask = new NewTask("Task 2", "Description 2", TaskStatus.IN_PROGRESS, user);

        updateTask = new UpdateTask("Updated Task", "Updated Description", TaskStatus.COMPLETED);
    }

    @Test
    public void testGetTaskById_Success() throws TaskExceptions {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskExceptions exception = assertThrows(TaskExceptions.class, () -> {
            taskService.getTaskById(1L);
        });
        assertEquals("No se encontró la tarea con ID: 1", exception.getMessage());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTasks_Success() {
        List<Task> tasks = List.of(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testCreateNewTask_Success() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.createNewTask(newTask);

        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTaskById_Success() throws TaskExceptions {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.updateTaskById(updateTask, 1L);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskExceptions exception = assertThrows(TaskExceptions.class, () -> {
            taskService.updateTaskById(updateTask, 1L);
        });
        assertEquals("No se ha encontrado la tarea: 1", exception.getMessage());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteTaskById_Success() {
        taskService.deleteTaskById(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

}