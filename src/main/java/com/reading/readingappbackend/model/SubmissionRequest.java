package com.reading.readingappbackend.model;

import java.util.List;

public class SubmissionRequest {

    private List<StudentAnswer> answers;

    public SubmissionRequest() {
    }

    public SubmissionRequest(List<StudentAnswer> answers) {
        this.answers = answers;
    }

    public List<StudentAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswer> answers) {
        this.answers = answers;
    }
}