package com.reading.readingappbackend.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // 题目 ID
    private Long assignmentId;    // 属于哪个作业
    private String type;          // "MCQ"（选择题） / "SHORT"（简答题）
    @Column(length = 1000)
    private String text;          // 题干
    @ElementCollection
    private List<String> options; // 选项（简答题可以为 null）
    @Column(length = 1000)
    private String correctAnswer; // 正确答案（之后也可以加 referenceAnswer 给 AI 用）
    private Integer score;      // 分值，比如 1 分、2 分
    private String difficulty;  // 难度，比如 "EASY" / "MEDIUM" / "HARD"

    public Question() {
    }

    public Question(Long id, Long assignmentId, String type,
                    String text, List<String> options, String correctAnswer) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.type = type;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    public Question(Long assignmentId, String type,
                    String text, List<String> options, String correctAnswer,Integer score,
                    String difficulty) {
        this.assignmentId = assignmentId;
        this.type = type;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.score = score;
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}