package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;

import java.util.UUID;

public record UpdateTask(String title, String description, Task.TaskStatus status) {
}
