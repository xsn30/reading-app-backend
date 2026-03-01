package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Assignment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.reading.readingappbackend.model.Question;
import org.springframework.web.bind.annotation.PathVariable;
import com.reading.readingappbackend.service.AssignmentService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.reading.readingappbackend.service.QuestionService;
import com.reading.readingappbackend.model.SubmissionRequest;
import com.reading.readingappbackend.model.SubmissionResult;
import com.reading.readingappbackend.service.SubmissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final QuestionService questionService;
    private final SubmissionService submissionService;
    public AssignmentController(AssignmentService assignmentService,
                                QuestionService questionService,
                                SubmissionService submissionService) {
        this.assignmentService = assignmentService;
        this.questionService = questionService;
        this.submissionService = submissionService;
    }

    @GetMapping("/assignments")
    public Page<Assignment> getAssignments(
            @RequestParam(defaultValue = "0") int page,         // 第几页，从 0 开始
            @RequestParam(defaultValue = "10") int size,        // 每页多少条
            @RequestParam(required = false) String bookTitle    // 可选：按书名模糊查
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return assignmentService.getAssignmentsPage(bookTitle, pageable);
    }
    @GetMapping("/assignments/{id}/questions")
    public List<Question> getQuestionsForAssignment(@PathVariable Long id) {
        return questionService.getQuestionsByAssignmentId(id);
    }
    @PostMapping("/assignments/{id}/questions")
    public Question createQuestionForAssignment(@PathVariable Long id,
                                                @RequestBody Question question) {
        return questionService.createQuestion(id, question);
    }
    @PostMapping("/assignments")
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }
    @PostMapping("/assignments/{id}/submit")
    public SubmissionResult submitAssignment(@PathVariable Long id,
                                             @RequestBody SubmissionRequest request) {
        return submissionService.gradeAssignment(id, request.getAnswers());
    }

    @PutMapping("/assignments/{id}")
    public Assignment updateAssignment(@PathVariable Long id,
                                       @RequestBody Assignment updated) {
        return assignmentService.updateAssignment(id, updated);
    }


    @DeleteMapping("/assignments/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId,
                                   @RequestBody Question updated) {
        return questionService.updateQuestion(questionId, updated);
    }


    @DeleteMapping("/questions/{questionId}")
    public void deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
    }
}