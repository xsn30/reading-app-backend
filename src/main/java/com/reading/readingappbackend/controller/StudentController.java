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
import com.reading.readingappbackend.model.Classroom;
import com.reading.readingappbackend.repository.ClassroomRepository;
import java.util.Map;
import java.util.LinkedHashMap;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student")
public class StudentController {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    public StudentController(SubmissionRepository submissionRepository,
                             SubmissionAnswerRepository submissionAnswerRepository,
                             AssignmentRepository assignmentRepository,
                             UserRepository userRepository,
                             ClassroomRepository classroomRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
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
    @GetMapping("/profile")
    public Object getStudentProfile(@RequestParam String username) {
        Optional<User> studentOptional = userRepository.findByUsername(username);

        if (studentOptional.isEmpty()) {
            return Map.of("error", "学生不存在");
        }

        User student = studentOptional.get();

        if (!"student".equals(student.getRole())) {
            return Map.of("error", "该账号不是学生");
        }

        Long classroomId = student.getClassroomId();
        String classroomName = "未加入班级";

        if (classroomId != null) {
            Optional<Classroom> classroomOptional = classroomRepository.findById(classroomId);
            if (classroomOptional.isPresent()) {
                classroomName = classroomOptional.get().getName();
            }
        }

        List<Submission> submissions =
                submissionRepository.findByStudentNameOrderBySubmittedAtDesc(username);

        int submissionCount = submissions.size();
        String latestSubmittedAt = submissions.isEmpty()
                ? ""
                : submissions.get(0).getSubmittedAt().toString();

        Map<String, Object> result = new java.util.LinkedHashMap<>();
        result.put("username", student.getUsername());
        result.put("role", student.getRole());
        result.put("classroomId", classroomId);
        result.put("classroomName", classroomName);
        result.put("submissionCount", submissionCount);
        result.put("latestSubmittedAt", latestSubmittedAt);

        result.put("displayName", student.getDisplayName());
        result.put("email", student.getEmail());
        result.put("phone", student.getPhone());
        result.put("birthday", student.getBirthday());

        return result;
    }
    @PutMapping("/profile")
    public Object updateStudentProfile(@RequestBody Map<String, String> request) {
        String username = request.get("username");

        if (username == null || username.isBlank()) {
            return Map.of("error", "username 不能为空");
        }

        Optional<User> studentOptional = userRepository.findByUsername(username);

        if (studentOptional.isEmpty()) {
            return Map.of("error", "学生不存在");
        }

        User student = studentOptional.get();

        if (!"student".equals(student.getRole())) {
            return Map.of("error", "该账号不是学生");
        }

        student.setDisplayName(request.get("displayName"));
        student.setEmail(request.get("email"));
        student.setPhone(request.get("phone"));
        student.setBirthday(request.get("birthday"));

        userRepository.save(student);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", "资料更新成功");
        result.put("username", student.getUsername());
        result.put("displayName", student.getDisplayName());
        result.put("email", student.getEmail());
        result.put("phone", student.getPhone());
        result.put("birthday", student.getBirthday());

        return result;
    }
}