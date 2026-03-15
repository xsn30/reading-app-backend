package com.reading.readingappbackend.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;        // 作业标题，比如“阅读《西游记》第1章”
    private String bookTitle;    // 书名，比如“西游记”
    private String chapter;      // 章节描述，比如“第1章 石猴出世”
    private LocalDate dueDate;   // 截止日期
    private Long classroomId;

    public Assignment() {
    }

    public Assignment(Long id, String title, String bookTitle,
                      String chapter, LocalDate dueDate, Long classroomId) {
        this.id = id;
        this.title = title;
        this.bookTitle = bookTitle;
        this.chapter = chapter;
        this.dueDate = dueDate;
        this.classroomId = classroomId;
    }
    public Assignment(String title, String bookTitle, String chapter, LocalDate dueDate,Long classroomId) {
        this.title = title;
        this.bookTitle = bookTitle;
        this.chapter = chapter;
        this.dueDate = dueDate;
        this.classroomId = classroomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookTitle() {
        return bookTitle;
    }
    public Long getClassroomId() {
        return classroomId;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }
}