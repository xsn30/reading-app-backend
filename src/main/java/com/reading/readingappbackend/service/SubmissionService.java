package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Question;
import com.reading.readingappbackend.model.QuestionResult;
import com.reading.readingappbackend.model.StudentAnswer;
import com.reading.readingappbackend.model.SubmissionResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmissionService {

    private final QuestionService questionService;
    private final ShortAnswerGrader shortAnswerGrader;

    public SubmissionService(QuestionService questionService,ShortAnswerGrader shortAnswerGrader) {
        this.questionService = questionService;
        this.shortAnswerGrader = shortAnswerGrader;
    }

    public SubmissionResult gradeAssignment(Long assignmentId, List<StudentAnswer> answers) {

        List<QuestionResult> results = new ArrayList<>();
        int totalScore = 0;
        int maxScore = 0;

        for (StudentAnswer studentAnswer : answers) {
            Long questionId = studentAnswer.getQuestionId();
            String answerText = studentAnswer.getAnswer();

            Question question = questionService.getQuestionById(questionId);

            // 找不到题目，或者题目不属于这个作业，直接跳过或记 0 分
            if (question == null || !question.getAssignmentId().equals(assignmentId)) {
                continue;
            }

            Integer questionScore = question.getScore();
            int scoreMax = (questionScore != null ? questionScore : 1);
            maxScore += scoreMax;

            String correct = question.getCorrectAnswer();
            int scoreEarned;
            boolean isCorrect;

            // —— 这里根据题型分流 ——
            if ("MCQ".equalsIgnoreCase(question.getType())) {
                // 选择题：沿用你原来的对比逻辑
                isCorrect = compareAnswers(correct, answerText);
                scoreEarned = isCorrect ? scoreMax : 0;
            } else {
                // 简答题 / 其他题型：交给 ShortAnswerGrader 处理
                scoreEarned = shortAnswerGrader.grade(question, answerText);
                isCorrect = (scoreEarned == scoreMax);  // 得到满分算“完全正确”
            }

            totalScore += scoreEarned;

            QuestionResult qr = new QuestionResult(
                    questionId,
                    answerText,
                    correct,
                    isCorrect,
                    scoreEarned,
                    scoreMax
            );
            results.add(qr);
        }
        return new SubmissionResult(assignmentId, totalScore, maxScore, results);
    }

    // 简单的对比逻辑：去掉前后空格，对大小写不敏感
    private boolean compareAnswers(String correct, String given) {
        if (correct == null || given == null) {
            return false;
        }

        String c = correct.trim();
        String g = given.trim();
        return c.equalsIgnoreCase(g);
    }
}