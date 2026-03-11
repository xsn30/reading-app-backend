package com.reading.readingappbackend.model;

import java.time.LocalDateTime;

public class StudentSubmissionStatus {

    private String studentUsername;
    private boolean submitted;
    private Long submissionId;
    private Integer totalScore;
    private Integer maxScore;
    private LocalDateTime submittedAt;

    public StudentSubmissionStatus() {
    }

    public StudentSubmissionStatus(String studentUsername,
                                   boolean submitted,
                                   Long submissionId,
                                   Integer totalScore,
                                   Integer maxScore,
                                   LocalDateTime submittedAt) {
        this.studentUsername = studentUsername;
        this.submitted = submitted;
        this.submissionId = submissionId;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.submittedAt = submittedAt;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}