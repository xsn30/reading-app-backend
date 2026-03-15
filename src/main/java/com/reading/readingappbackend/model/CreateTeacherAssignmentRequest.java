package com.reading.readingappbackend.model;

import java.time.LocalDate;

public class CreateTeacherAssignmentRequest {

    private String title;
    private String bookTitle;
    private String chapter;
    private LocalDate dueDate;
    private Long classroomId;
    private String teacherUsername;

    public CreateTeacherAssignmentRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getChapter() {
        return chapter;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
    }
}