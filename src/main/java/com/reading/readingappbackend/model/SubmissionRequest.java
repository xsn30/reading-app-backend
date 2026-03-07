package com.reading.readingappbackend.model;

import java.util.List;

public class SubmissionRequest {
    private String studentName;
    private List<StudentAnswer> answers;

    public SubmissionRequest() {
    }

    public SubmissionRequest(String studentName, List<StudentAnswer> answers) {
        this.answers = answers;
        this.studentName = studentName;
    }

    public List<StudentAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswer> answers) {
        this.answers = answers;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}