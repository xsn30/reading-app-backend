package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Question;

public interface ShortAnswerGrader {

    /**
     * 对简答题进行评分。
     *
     * @param question      题目本身（里面有标准答案、分值等）
     * @param studentAnswer 学生的作答
     * @return 本题学生获得的分数（0 ~ question.score）
     */
    int grade(Question question, String studentAnswer);
}