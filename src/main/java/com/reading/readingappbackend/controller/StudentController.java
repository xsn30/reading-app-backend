package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Submission;
import com.reading.readingappbackend.model.SubmissionAnswer;
import com.reading.readingappbackend.repository.SubmissionAnswerRepository;
import com.reading.readingappbackend.repository.SubmissionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;

    public StudentController(SubmissionRepository submissionRepository,
                             SubmissionAnswerRepository submissionAnswerRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
    }

    // 1. 查看某个学生自己的提交记录列表
    @GetMapping("/submissions")
    public List<Submission> getStudentSubmissions(@RequestParam String studentUsername) {
        return submissionRepository.findByStudentNameOrderBySubmittedAtDesc(studentUsername);
    }

    // 2. 查看某次提交的详细答案
    @GetMapping("/submissions/{submissionId}")
    public List<SubmissionAnswer> getStudentSubmissionDetail(@PathVariable Long submissionId) {
        return submissionAnswerRepository.findBySubmissionId(submissionId);
    }
}