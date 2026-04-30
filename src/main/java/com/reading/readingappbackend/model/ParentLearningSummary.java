package com.reading.readingappbackend.model;

import java.time.LocalDateTime;
import java.util.List;

public class ParentLearningSummary {

    private int totalSubmissions;
    private double averageScore;
    private Integer latestScore;
    private Integer latestMaxScore;
    private LocalDateTime latestSubmittedAt;
    private List<AssignmentTrendItem> assignmentTrend;

    public ParentLearningSummary() {
    }

    public ParentLearningSummary(int totalSubmissions,
                                 double averageScore,
                                 Integer latestScore,
                                 Integer latestMaxScore,
                                 LocalDateTime latestSubmittedAt,
                                 List<AssignmentTrendItem> assignmentTrend) {
        this.totalSubmissions = totalSubmissions;
        this.averageScore = averageScore;
        this.latestScore = latestScore;
        this.latestMaxScore = latestMaxScore;
        this.latestSubmittedAt = latestSubmittedAt;
        this.assignmentTrend = assignmentTrend;
    }

    public int getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(int totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getLatestScore() {
        return latestScore;
    }

    public void setLatestScore(Integer latestScore) {
        this.latestScore = latestScore;
    }

    public Integer getLatestMaxScore() {
        return latestMaxScore;
    }

    public void setLatestMaxScore(Integer latestMaxScore) {
        this.latestMaxScore = latestMaxScore;
    }

    public LocalDateTime getLatestSubmittedAt() {
        return latestSubmittedAt;
    }

    public void setLatestSubmittedAt(LocalDateTime latestSubmittedAt) {
        this.latestSubmittedAt = latestSubmittedAt;
    }

    public List<AssignmentTrendItem> getAssignmentTrend() {
        return assignmentTrend;
    }

    public void setAssignmentTrend(List<AssignmentTrendItem> assignmentTrend) {
        this.assignmentTrend = assignmentTrend;
    }
}