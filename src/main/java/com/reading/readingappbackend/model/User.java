package com.reading.readingappbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;   // student / teacher / parent / school
    private String linkedStudentUsername;

    public User() {
    }

    public User(String username, String password, String role, String linkedStudentUsername) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedStudentUsername = linkedStudentUsername;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    public String getLinkedStudentUsername() { return linkedStudentUsername; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setLinkedStudentUsername(String linkedStudentUsername) {
        this.linkedStudentUsername = linkedStudentUsername;
    }
}