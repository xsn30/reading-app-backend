package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Question;
import org.springframework.stereotype.Service;

/**
 * 现在先用“假 AI”来打分：
 * - 选择题：答案完全相同就拿满分，否则 0 分
 * - 简答题：答案完全相同拿满分，否则 0 分
 *
 * 以后你要接入真正的模型，只要改这个类的实现就行了，
 * SubmissionService 不用动。
 */
@Service
public class MockAiGrader implements AiGrader {

    @Override
    public int grade(Question question, String studentAnswer) {
        // 没有分值就当 1 分题
        Integer scoreObj = question.getScore();
        int maxScore = (scoreObj != null ? scoreObj : 1);

        String correct = question.getCorrectAnswer();
        if (correct == null || studentAnswer == null) {
            return 0;
        }

        String c = correct.trim();
        String s = studentAnswer.trim();

        // 暂时：所有题型都用“完全相等”来判断（你可以以后再做更聪明的）
        boolean equal = c.equalsIgnoreCase(s);

        return equal ? maxScore : 0;
    }
}