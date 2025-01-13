package com.mindhub.todolist.models;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum TaskStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED;
    }

    private String title, description;

    private TaskStatus status;

    @ManyToOne
    private UserModel userModel;

    public Task () {}

    public Task(String title, String description, TaskStatus status, UserModel userModel) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.userModel = userModel;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public UserModel getUser() {
        return userModel;
    }

    public void setUser(UserModel userModel) {
        this.userModel = userModel;
    }
}
