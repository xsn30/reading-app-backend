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
import com.reading.readingappbackend.model.Classroom;
import com.reading.readingappbackend.model.User;
import com.reading.readingappbackend.repository.ClassroomRepository;
import com.reading.readingappbackend.repository.UserRepository;
import com.reading.readingappbackend.model.StudentSubmissionStatus;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;

    public TeacherController(SubmissionRepository submissionRepository,
                             SubmissionAnswerRepository submissionAnswerRepository,
                             AssignmentRepository assignmentRepository,
                             QuestionRepository questionRepository,
                             ClassroomRepository classroomRepository,
                             UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
        this.assignmentRepository = assignmentRepository;
        this.questionRepository = questionRepository;
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/assignments/{assignmentId}/submissions")
    public List<Submission> getSubmissions(@PathVariable Long assignmentId,
                                           @RequestParam String teacherUsername) {

        List<Submission> allSubmissions = submissionRepository.findByAssignmentId(assignmentId);

        return allSubmissions.stream()
                .filter(submission -> {
                    Optional<User> studentOptional = userRepository.findByUsername(submission.getStudentName());

                    if (studentOptional.isEmpty()) {
                        return false;
                    }

                    User student = studentOptional.get();

                    if (student.getClassroomId() == null) {
                        return false;
                    }

                    Optional<Classroom> classroomOptional = classroomRepository.findById(student.getClassroomId());

                    if (classroomOptional.isEmpty()) {
                        return false;
                    }

                    Classroom classroom = classroomOptional.get();

                    return teacherUsername.equals(classroom.getTeacherUsername());
                })
                .toList();
    }
    @GetMapping("/assignments/{assignmentId}/submission-status")
    public List<StudentSubmissionStatus> getSubmissionStatus(@PathVariable Long assignmentId,
                                                             @RequestParam String teacherUsername) {

        // 1. 先找到这个老师创建的所有班级
        List<Classroom> classrooms = classroomRepository.findByTeacherUsername(teacherUsername);

        // 2. 找到这些班级里的所有学生
        List<User> students = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            students.addAll(userRepository.findByClassroomId(classroom.getId()));
        }

        // 3. 查这个作业的所有提交
        List<Submission> submissions = submissionRepository.findByAssignmentId(assignmentId);

        // 4. 方便按 studentName 找 submission
        Map<String, Submission> submissionMap = new HashMap<>();
        for (Submission submission : submissions) {
            submissionMap.put(submission.getStudentName(), submission);
        }

        // 5. 组装状态结果
        List<StudentSubmissionStatus> result = new ArrayList<>();

        for (User student : students) {
            Submission submission = submissionMap.get(student.getUsername());

            if (submission == null) {
                result.add(new StudentSubmissionStatus(
                        student.getUsername(),
                        false,
                        null,
                        null,
                        null,
                        null
                ));
            } else {
                result.add(new StudentSubmissionStatus(
                        student.getUsername(),
                        true,
                        submission.getId(),
                        submission.getTotalScore(),
                        submission.getMaxScore(),
                        submission.getSubmittedAt()
                ));
            }
        }

        return result;
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