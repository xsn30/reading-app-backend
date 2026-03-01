package com.reading.readingappbackend.model;

public class StudentAnswer {

    private Long questionId;
    private String answer;  // 学生作答

    public StudentAnswer() {
    }

    public StudentAnswer(Long questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}