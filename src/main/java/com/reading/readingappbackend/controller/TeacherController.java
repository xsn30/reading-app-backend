package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Assignment;
import com.reading.readingappbackend.model.Submission;
import com.reading.readingappbackend.model.SubmissionAnswer;
import com.reading.readingappbackend.repository.AssignmentRepository;
import com.reading.readingappbackend.repository.SubmissionAnswerRepository;
import com.reading.readingappbackend.repository.SubmissionRepository;
import org.springframework.web.bind.annotation.*;
import com.reading.readingappbackend.repository.QuestionRepository;
import com.reading.readingappbackend.model.Question;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;

    public TeacherController(SubmissionRepository submissionRepository,
                             SubmissionAnswerRepository submissionAnswerRepository,
                             AssignmentRepository assignmentRepository,
                             QuestionRepository questionRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
        this.assignmentRepository = assignmentRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/assignments/{assignmentId}/submissions")
    public List<Submission> getSubmissions(@PathVariable Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }
    @GetMapping("/submissions/{submissionId}")
    public List<SubmissionAnswer> getSubmissionAnswers(@PathVariable Long submissionId) {
        return submissionAnswerRepository.findBySubmissionId(submissionId);
    }
    @GetMapping("/assignments")
    public List<Assignment> getAssignments() {
        return assignmentRepository.findAll();
    }
    @PostMapping("/assignments")
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
    @PostMapping("/assignments/{assignmentId}/questions")
    public Question createQuestion(
            @PathVariable Long assignmentId,
            @RequestBody Question question
    ) {
        // 强制把题目归属到这个 assignmentId（防止前端乱传）
        question.setAssignmentId(assignmentId);

        // 如果是 SHORT，没有 options，就置空/空列表（可选）
        if (!"MCQ".equalsIgnoreCase(question.getType())) {
            question.setOptions(List.of());
        }

        return questionRepository.save(question);
    }
}