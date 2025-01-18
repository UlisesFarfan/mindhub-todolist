package com.mindhub.todolist;

import com.mindhub.todolist.models.RoleType;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserModel;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.services.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			UserModel admin = new UserModel("ADMIN", passwordEncoder.encode("admin1234"), "admin@admin.com", RoleType.ADMIN);
			userRepository.save(admin);
			UserModel user = new UserModel("USER", passwordEncoder.encode("user1234"), "user@user.com", RoleType.USER);
			userRepository.save(user);
			Task task = new Task("Nueva tarea test", "terminar de realizar los test", Task.TaskStatus.IN_PROGRESS, user);
			Task task1 = new Task("Nueva tarea test", "terminar de realizar los test", Task.TaskStatus.IN_PROGRESS, user);
			Task task2 = new Task("Nueva tarea test", "terminar de realizar los test", Task.TaskStatus.IN_PROGRESS, admin);
			taskRepository.save(task);
			taskRepository.save(task1);
			taskRepository.save(task2);
		};
	}

}
