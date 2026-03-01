package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Assignment;
import com.reading.readingappbackend.repository.AssignmentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.reading.readingappbackend.repository.QuestionRepository;


import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;


    public AssignmentService(AssignmentRepository assignmentRepository,QuestionRepository questionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.questionRepository = questionRepository;
    }

    // ⭐ 启动时初始化两条假数据（只有在表里没数据时才插入）
    @PostConstruct
    public void initData() {
        if (assignmentRepository.count() == 0) {
            Assignment a1 = new Assignment(
                    "阅读《西游记》第1章并完成练习题",
                    "西游记",
                    "第1章 石猴出世",
                    LocalDate.of(2026, 3, 1)
            );

            Assignment a2 = new Assignment(
                    "阅读《哈利·波特》第一章并回答问题",
                    "哈利·波特与魔法石",
                    "Chapter 1: The Boy Who Lived",
                    LocalDate.of(2026, 3, 5)
            );

            assignmentRepository.save(a1);
            assignmentRepository.save(a2);
        }
    }

    // ✅ 查询全部作业：从数据库里查
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // ✅ 根据 id 查作业：从数据库里查
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    // ✅ 创建新作业：保存到数据库
    public Assignment createAssignment(Assignment assignment) {
        // 不需要再自己 setId，自增交给数据库/JPA
        return assignmentRepository.save(assignment);
    }
    public Page<Assignment> getAssignmentsPage(String bookTitleFilter, Pageable pageable) {
        if (bookTitleFilter != null && !bookTitleFilter.isBlank()) {
            return assignmentRepository.findByBookTitleContainingIgnoreCase(bookTitleFilter, pageable);
        } else {
            return assignmentRepository.findAll(pageable);
        }

    }
    public void deleteAssignment(Long id) {
        questionRepository.deleteByAssignmentId(id);
        assignmentRepository.deleteById(id);
    }
    public Assignment updateAssignment(Long id, Assignment updated) {
        return assignmentRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setBookTitle(updated.getBookTitle());
                    existing.setChapter(updated.getChapter());
                    existing.setDueDate(updated.getDueDate());
                    return assignmentRepository.save(existing);
                })
                .orElse(null);
    }
}