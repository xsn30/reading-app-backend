package com.reading.readingappbackend.model;

public class RegisterRequest {

    private String username;
    private String password;
    private String role;
    private String linkedStudentUsername;

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getLinkedStudentUsername() {
        return linkedStudentUsername;
    }

    public void setLinkedStudentUsername(String linkedStudentUsername) {
        this.linkedStudentUsername = linkedStudentUsername;
    }
}