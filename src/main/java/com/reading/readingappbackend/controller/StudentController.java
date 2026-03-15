package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Submission;
import com.reading.readingappbackend.model.SubmissionAnswer;
import com.reading.readingappbackend.repository.SubmissionAnswerRepository;
import com.reading.readingappbackend.repository.SubmissionRepository;
import org.springframework.web.bind.annotation.*;
import com.reading.readingappbackend.model.Assignment;
import com.reading.readingappbackend.model.User;
import com.reading.readingappbackend.repository.AssignmentRepository;
import com.reading.readingappbackend.repository.UserRepository;
import java.util.Optional;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public StudentController(SubmissionRepository submissionRepository,
                             SubmissionAnswerRepository submissionAnswerRepository,
                             AssignmentRepository assignmentRepository,
                             UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
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
    @GetMapping("/assignments")
    public List<Assignment> getStudentAssignments(@RequestParam String studentUsername) {
        Optional<User> studentOptional = userRepository.findByUsername(studentUsername);

        if (studentOptional.isEmpty()) {
            return List.of();
        }

        User student = studentOptional.get();

        if (student.getClassroomId() == null) {
            return List.of();
        }

        return assignmentRepository.findByClassroomIdOrderByDueDateAsc(student.getClassroomId());
    }
}