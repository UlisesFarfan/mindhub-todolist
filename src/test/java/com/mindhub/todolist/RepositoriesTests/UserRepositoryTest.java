package com.mindhub.todolist.RepositoriesTests;

import com.mindhub.todolist.config.TestConfig;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestConfig.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRepository() {
        UserModel userEntity = new UserModel("Ulises Farfan", "password", "ulisesfarfan@example.com");
        UserModel savedEntity = userRepository.save(userEntity);

        assertNotNull(savedEntity.getId());
    }

}