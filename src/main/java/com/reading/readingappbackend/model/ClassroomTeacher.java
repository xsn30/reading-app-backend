package com.reading.readingappbackend.model;

import jakarta.persistence.*;

@Entity
public class ClassroomTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long classroomId;

    private String teacherUsername;

    private String roleInClass; // HOMEROOM / SUBJECT

    public ClassroomTeacher() {
    }

    public ClassroomTeacher(Long classroomId, String teacherUsername, String roleInClass) {
        this.classroomId = classroomId;
        this.teacherUsername = teacherUsername;
        this.roleInClass = roleInClass;
    }

    public Long getId() {
        return id;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public String getRoleInClass() {
        return roleInClass;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }

    public void setRoleInClass(String roleInClass) {
        this.roleInClass = roleInClass;
    }
}