package com.reading.readingappbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long assignmentId;

    private String studentName;

    private Integer totalScore;

    private Integer maxScore;

    private LocalDateTime submittedAt;

    public Submission() {}

    public Submission(Long assignmentId, String studentName, Integer totalScore, Integer maxScore) {
        this.assignmentId = assignmentId;
        this.studentName = studentName;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getAssignmentId() { return assignmentId; }
    public String getStudentName() { return studentName; }
    public Integer getTotalScore() { return totalScore; }
    public Integer getMaxScore() { return maxScore; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
}