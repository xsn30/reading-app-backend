package com.reading.readingappbackend.model;

public class CreateClassroomRequest {

    private String name;
    private String teacherUsername;

    public CreateClassroomRequest() {
    }

    public String getName() {
        return name;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }
}