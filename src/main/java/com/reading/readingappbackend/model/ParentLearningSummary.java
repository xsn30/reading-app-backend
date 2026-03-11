package com.reading.readingappbackend.model;

import java.time.LocalDateTime;

public class ParentLearningSummary {

    private int totalSubmissions;
    private double averageScore;
    private Integer latestScore;
    private Integer latestMaxScore;
    private LocalDateTime latestSubmittedAt;

    public ParentLearningSummary() {
    }

    public ParentLearningSummary(int totalSubmissions,
                                 double averageScore,
                                 Integer latestScore,
                                 Integer latestMaxScore,
                                 LocalDateTime latestSubmittedAt) {
        this.totalSubmissions = totalSubmissions;
        this.averageScore = averageScore;
        this.latestScore = latestScore;
        this.latestMaxScore = latestMaxScore;
        this.latestSubmittedAt = latestSubmittedAt;
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
}