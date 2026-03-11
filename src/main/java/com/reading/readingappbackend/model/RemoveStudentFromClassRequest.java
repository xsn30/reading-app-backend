package com.reading.readingappbackend.model;

public class RemoveStudentFromClassRequest {

    private String homeroomTeacherUsername;
    private String studentUsername;

    public RemoveStudentFromClassRequest() {
    }

    public String getHomeroomTeacherUsername() {
        return homeroomTeacherUsername;
    }

    public void setHomeroomTeacherUsername(String homeroomTeacherUsername) {
        this.homeroomTeacherUsername = homeroomTeacherUsername;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
}