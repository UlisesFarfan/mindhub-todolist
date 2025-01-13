package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserModel;


public record NewTask (String title, String description, Task.TaskStatus status, UserModel userModel) {
}
