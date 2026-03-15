package com.reading.readingappbackend.service;

import com.reading.readingappbackend.model.Assignment;
import com.reading.readingappbackend.repository.AssignmentRepository;
import com.reading.readingappbackend.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.reading.readingappbackend.model.Submission;
import com.reading.readingappbackend.repository.SubmissionAnswerRepository;
import com.reading.readingappbackend.repository.SubmissionRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;
    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             QuestionRepository questionRepository,
                             SubmissionRepository submissionRepository,
                             SubmissionAnswerRepository submissionAnswerRepository) {
        this.assignmentRepository = assignmentRepository;
        this.questionRepository = questionRepository;
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
    }

    // 暂时不初始化默认作业
    // 作业将由老师通过接口创建
    @PostConstruct
    public void initData() {
    }

    // 查询全部作业
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // 根据 id 查作业
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    // 创建新作业
    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    // 分页查询作业（保留原来的搜索逻辑）
    public Page<Assignment> getAssignmentsPage(String bookTitleFilter, Pageable pageable) {
        if (bookTitleFilter != null && !bookTitleFilter.isBlank()) {
            return assignmentRepository.findByBookTitleContainingIgnoreCase(bookTitleFilter, pageable);
        } else {
            return assignmentRepository.findAll(pageable);
        }
    }

    // 删除作业
    @Transactional
    public void deleteAssignment(Long id) {
        List<Submission> submissions = submissionRepository.findByAssignmentId(id);

        for (Submission submission : submissions) {
            submissionAnswerRepository.deleteBySubmissionId(submission.getId());
        }

        submissionRepository.deleteByAssignmentId(id);
        questionRepository.deleteByAssignmentId(id);
        assignmentRepository.deleteById(id);
    }

    // 更新作业
    public Assignment updateAssignment(Long id, Assignment updated) {
        return assignmentRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setBookTitle(updated.getBookTitle());
                    existing.setChapter(updated.getChapter());
                    existing.setDueDate(updated.getDueDate());
                    existing.setClassroomId(updated.getClassroomId());
                    return assignmentRepository.save(existing);
                })
                .orElse(null);
    }
}