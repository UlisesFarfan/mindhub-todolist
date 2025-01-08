package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.User;


public record NewTask (String title, String description, Task.TaskStatus status, User user) {
}
