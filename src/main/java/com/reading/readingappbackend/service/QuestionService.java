package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Question;
import com.reading.readingappbackend.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    // ⭐ 构造函数注入 Repository
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void initData() {
        if (questionRepository.count() == 0) {
            // 作业 1：西游记
            Question q1 = new Question(
                    1L,
                    "MCQ",
                    "孙悟空是从哪里出生的？",
                    List.of("A. 花果山的一块石头", "B. 东海龙宫", "C. 天庭", "D. 西天极乐世界"),
                    "A",
                    1,            // score
                    "EASY"        // difficulty
            );

            Question q2 = new Question(
                    1L,
                    "SHORT",
                    "用一句话概括这一章的主要内容。",
                    null,
                    "这章主要讲的是石猴出生并成为猴王的过程",
                    2,            // 简答题可以多一点分
                    "MEDIUM"
            );

            // 作业 2：哈利·波特
            Question q3 = new Question(
                    2L,
                    "MCQ",
                    "德思礼一家最害怕别人知道什么？",
                    List.of("A. 他们有个侄子哈利", "B. 他们和魔法世界有关系", "C. 他们住在女贞路", "D. 他们非常有钱"),
                    "B",
                    1,
                    "EASY"
            );

            Question q4 = new Question(
                    2L,
                    "SHORT",
                    "为什么这一章叫做“The Boy Who Lived”？",
                    null,
                    "因为哈利在伏地魔的攻击中幸存下来，被称为“活下来的男孩”",
                    2,
                    "MEDIUM"
            );

            questionRepository.save(q1);
            questionRepository.save(q2);
            questionRepository.save(q3);
            questionRepository.save(q4);
        }
    }

    // ✅ 按作业 id 查询题目
    public List<Question> getQuestionsByAssignmentId(Long assignmentId) {
        return questionRepository.findByAssignmentId(assignmentId);
    }

    // ✅ 创建新题目
    public Question createQuestion(Long assignmentId, Question question) {
        question.setAssignmentId(assignmentId);
        return questionRepository.save(question);
    }

    // ✅ 根据题目 id 查题（给判分用）
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
    public Question updateQuestion(Long id, Question updated) {
        return questionRepository.findById(id)
                .map(existing -> {
                    existing.setType(updated.getType());
                    existing.setText(updated.getText());
                    existing.setOptions(updated.getOptions());
                    existing.setCorrectAnswer(updated.getCorrectAnswer());
                    // 等下我们会加 score / difficulty，这里一起 set
                    return questionRepository.save(existing);
                })
                .orElse(null);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}