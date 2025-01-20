package com.mindhub.todolist.RepositoriesTests;

import com.mindhub.todolist.config.TestConfig;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestConfig.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        UserModel user = new UserModel();
        user.setName("Ulises Farfan");
        user.setEmail("ulisesfarfan@example.com");
        user.setPassword("password");

        UserModel savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Ulises Farfan", savedUser.getName());
    }

    @Test
    public void testFindById_Success() {
        UserModel user = new UserModel();
        user.setName("Ulises Farfan");
        user.setEmail("ulisesfarfan2@example.com");
        user.setPassword("password1234");

        UserModel savedUser = userRepository.save(user);

        Optional<UserModel> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("Ulises Farfan", foundUser.get().getName());
    }

    @Test
    public void testFindAll() {
        var users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    public void testFindByEmail_Success() {
        Optional<UserModel> foundUser = userRepository.findByEmail("admin@admin.com");

        assertTrue(foundUser.isPresent());
        assertEquals("ADMIN", foundUser.get().getName());
    }

    @Test
    public void testFindByEmail_NotFound() {
        Optional<UserModel> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testDeleteById() {
        UserModel user = new UserModel();
        user.setName("Deletable User");
        user.setEmail("delete@example.com");
        user.setPassword("deletablepassword");

        UserModel savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        Optional<UserModel> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());
    }


}