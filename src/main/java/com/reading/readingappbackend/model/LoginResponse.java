package com.reading.readingappbackend.model;

public class LoginResponse {

    private Long id;
    private String username;
    private String role;
    private String linkedStudentUsername;
    private String phone;

    public LoginResponse() {
    }

    public LoginResponse(Long id, String username, String role, String linkedStudentUsername, String phone) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.linkedStudentUsername = linkedStudentUsername;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
    public String getLinkedStudentUsername() {
        return linkedStudentUsername;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setLinkedStudentUsername(String linkedStudentUsername) {
        this.linkedStudentUsername = linkedStudentUsername;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}