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
    private Long classroomId;

    private String displayName;
    private String email;
    private String phone;
    private String birthday;

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

    public String getLinkedStudentUsername() {
        return linkedStudentUsername;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

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

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}