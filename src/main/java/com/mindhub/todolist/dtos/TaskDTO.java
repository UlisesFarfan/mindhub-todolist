package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.Task;

import java.util.UUID;

public class TaskDTO {
    private final Long id;
    private final String title, description;
    private final Task.TaskStatus status;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Task.TaskStatus getStatus() {
        return status;
    }
}
