package com.reading.readingappbackend.model;

public class RegisterRequest {

    private String password;
    private String role;
    private String linkedStudentUsername;
    private String phone;

    public RegisterRequest() {
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}