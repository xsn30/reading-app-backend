package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Submission;
import com.reading.readingappbackend.model.SubmissionAnswer;
import com.reading.readingappbackend.repository.SubmissionAnswerRepository;
import com.reading.readingappbackend.repository.SubmissionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/parent")
public class ParentController {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;

    public ParentController(SubmissionRepository submissionRepository,
                            SubmissionAnswerRepository submissionAnswerRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
    }

    @GetMapping("/students/{studentName}/submissions")
    public List<Submission> getStudentSubmissions(@PathVariable String studentName) {
        return submissionRepository.findByStudentNameOrderBySubmittedAtDesc(studentName);
    }

    @GetMapping("/submissions/{submissionId}")
    public List<SubmissionAnswer> getSubmissionAnswers(@PathVariable Long submissionId) {
        return submissionAnswerRepository.findBySubmissionId(submissionId);
    }
}