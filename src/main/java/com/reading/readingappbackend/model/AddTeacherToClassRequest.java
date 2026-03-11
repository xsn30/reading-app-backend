package com.reading.readingappbackend.model;

public class AddTeacherToClassRequest {

    private String homeroomTeacherUsername;
    private String teacherUsername;

    public AddTeacherToClassRequest() {
    }

    public String getHomeroomTeacherUsername() {
        return homeroomTeacherUsername;
    }

    public void setHomeroomTeacherUsername(String homeroomTeacherUsername) {
        this.homeroomTeacherUsername = homeroomTeacherUsername;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }
}