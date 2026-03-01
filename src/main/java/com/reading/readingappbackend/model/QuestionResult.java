package com.reading.readingappbackend.model;

public class QuestionResult {

    private Long questionId;
    private String studentAnswer;
    private String correctAnswer;
    private boolean correct;
    private int scoreEarned;
    private int scoreMax;

    public QuestionResult() {
    }

    public QuestionResult(Long questionId,
                          String studentAnswer,
                          String correctAnswer,
                          boolean correct,
                          int scoreEarned,
                          int scoreMax) {
        this.questionId = questionId;
        this.studentAnswer = studentAnswer;
        this.correctAnswer = correctAnswer;
        this.correct = correct;
        this.scoreEarned = scoreEarned;
        this.scoreMax = scoreMax;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getScoreEarned() {
        return scoreEarned;
    }

    public void setScoreEarned(int scoreEarned) {
        this.scoreEarned = scoreEarned;
    }

    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }
}