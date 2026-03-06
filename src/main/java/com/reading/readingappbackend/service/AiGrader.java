package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Question;

/**
 * 以后无论是真正的 AI，还是现在的假实现，都实现这个接口。
 */
public interface AiGrader {

    /**
     * 给一题打分
     * @param question      题目本身（含类型、分值、标准答案等）
     * @param studentAnswer 学生的作答
     * @return 实际得分（0 ~ question.score）
     */
    int grade(Question question, String studentAnswer);
}