package com.reading.readingappbackend.model;

import jakarta.persistence.*;

@Entity
public class ParentStudentLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parentUsername;
    private String studentUsername;

    public ParentStudentLink() {
    }

    public ParentStudentLink(String parentUsername, String studentUsername) {
        this.parentUsername = parentUsername;
        this.studentUsername = studentUsername;
    }

    public Long getId() {
        return id;
    }

    public String getParentUsername() {
        return parentUsername;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParentUsername(String parentUsername) {
        this.parentUsername = parentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
}