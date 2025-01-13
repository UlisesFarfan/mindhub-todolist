package com.mindhub.todolist.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, password;

    @Column(unique = true)
    private String email;

    private RoleType role = RoleType.USER;

    @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> task = new HashSet<>();

    public UserModel() {}

    public UserModel(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public UserModel(String name, String password, String email, RoleType roleType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = roleType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Set<Task> getTask() {
        return task;
    }

    public void setTask(Set<Task> task) {
        this.task = task;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
