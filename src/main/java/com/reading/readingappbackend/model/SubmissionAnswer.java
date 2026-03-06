package com.reading.readingappbackend.model;

import jakarta.persistence.*;

@Entity
public class SubmissionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long submissionId;

    private Long questionId;

    private String studentAnswer;

    private String correctAnswer;

    private Boolean correct;

    private Integer scoreEarned;

    private Integer scoreMax;

    public SubmissionAnswer() {}

    public SubmissionAnswer(Long submissionId, Long questionId, String studentAnswer,
                            String correctAnswer, Boolean correct,
                            Integer scoreEarned, Integer scoreMax) {
        this.submissionId = submissionId;
        this.questionId = questionId;
        this.studentAnswer = studentAnswer;
        this.correctAnswer = correctAnswer;
        this.correct = correct;
        this.scoreEarned = scoreEarned;
        this.scoreMax = scoreMax;
    }
    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }
    public Long getSubmissionId() {
        return submissionId;
    }

    public Long getQuestionId() {
        return questionId;
    }
    public String getStudentAnswer() {
        return studentAnswer;
    }
    public String getCorrectAnswer() { return correctAnswer; }

    public Boolean getCorrect() {
        return correct;
    }

    public Integer getScoreEarned() {
        return scoreEarned;
    }
    public Integer getScoreMax() { return scoreMax; }
}