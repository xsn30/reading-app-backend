package com.reading.readingappbackend.model;

import java.util.List;

public class SubmissionResult {

    private Long assignmentId;
    private int totalScore;
    private int maxScore;
    private List<QuestionResult> results;

    public SubmissionResult() {
    }

    public SubmissionResult(Long assignmentId,
                            int totalScore,
                            int maxScore,
                            List<QuestionResult> results) {
        this.assignmentId = assignmentId;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.results = results;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public List<QuestionResult> getResults() {
        return results;
    }

    public void setResults(List<QuestionResult> results) {
        this.results = results;
    }
}