package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.UserModel;

public class UserDTO {
    private final Long id;
    private final String name, email;
    public UserDTO(UserModel userModel) {
        id = userModel.getId();
        name = userModel.getName();
        email = userModel.getEmail();
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
