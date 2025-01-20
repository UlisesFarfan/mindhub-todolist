package com.mindhub.todolist.RepositoriesTests;

import com.mindhub.todolist.config.TestConfig;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestConfig.class)
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveTask() {
        Task task = new Task();
        Task savedTask = taskRepository.save(task);
        assertNotNull(savedTask.getId());
    }

    @Test
    public void testFindById_Success() {
        Task task = new Task();
        task.setTitle("Sample Task");
        task.setDescription("Sample Description");
        Task savedTask = taskRepository.save(task);

        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

        assertTrue(foundTask.isPresent());
        assertEquals("Sample Task", foundTask.get().getTitle());
    }

    @Test
    public void testFindAll() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        Task task2 = new Task();
        task2.setTitle("Task 2");

        taskRepository.save(task1);
        taskRepository.save(task2);

        var tasks = taskRepository.findAll();

        assertEquals(5, tasks.size());
    }

    @Test
    public void testDeleteById() {
        Task task = new Task();
        task.setTitle("Task to be deleted");
        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());

        Optional<Task> deletedTask = taskRepository.findById(savedTask.getId());
        assertFalse(deletedTask.isPresent());
    }
}
