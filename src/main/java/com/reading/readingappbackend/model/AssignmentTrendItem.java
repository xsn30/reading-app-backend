package com.reading.readingappbackend.model;

public class AssignmentTrendItem {

    private Long assignmentId;
    private String assignmentTitle;
    private Integer bestScore;
    private Integer maxScore;
    private double scoreRate;

    public AssignmentTrendItem() {
    }

    public AssignmentTrendItem(Long assignmentId,
                               String assignmentTitle,
                               Integer bestScore,
                               Integer maxScore,
                               double scoreRate) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.bestScore = bestScore;
        this.maxScore = maxScore;
        this.scoreRate = scoreRate;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public Integer getBestScore() {
        return bestScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public double getScoreRate() {
        return scoreRate;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public void setBestScore(Integer bestScore) {
        this.bestScore = bestScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public void setScoreRate(double scoreRate) {
        this.scoreRate = scoreRate;
    }
}