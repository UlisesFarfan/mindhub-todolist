package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.User;

public class UserDTO {
    private final Long id;
    private final String name, email;
    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
