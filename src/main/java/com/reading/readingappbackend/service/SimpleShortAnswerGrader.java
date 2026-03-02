package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Question;
import org.springframework.stereotype.Service;

@Service
public class SimpleShortAnswerGrader implements ShortAnswerGrader {

    @Override
    public int grade(Question question, String studentAnswer) {
        String correct = question.getCorrectAnswer();
        if (correct == null || studentAnswer == null) {
            return 0;
        }

        String c = correct.trim();
        String g = studentAnswer.trim();

        // 题目设置的分值，没设置就默认 1 分
        Integer questionScore = question.getScore();
        int maxScore = (questionScore != null ? questionScore : 1);

        // 现在先用“完全相等 → 满分”作为简答题的临时规则
        if (c.equals(g)) {
            return maxScore;
        }

        // 这里以后就可以换成：调用 AI，大致匹配就给部分分
        return 0;
    }
}