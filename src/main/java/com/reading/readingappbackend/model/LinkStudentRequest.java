package com.reading.readingappbackend.model;

public class LinkStudentRequest {

    private String parentUsername;
    private String studentUsername;

    public LinkStudentRequest() {
    }

    public String getParentUsername() {
        return parentUsername;
    }

    public void setParentUsername(String parentUsername) {
        this.parentUsername = parentUsername;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
}