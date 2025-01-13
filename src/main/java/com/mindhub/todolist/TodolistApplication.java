package com.mindhub.todolist;

import com.mindhub.todolist.models.RoleType;
import com.mindhub.todolist.models.UserModel;
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
	public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			UserModel user = new UserModel("ADMIN", passwordEncoder.encode("admin1234"), "admin@admin.com", RoleType.ADMIN);
			userRepository.save(user);
		};
	}

}
