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
import com.reading.readingappbackend.model.ClassroomTeacher;
import com.reading.readingappbackend.model.CreateTeacherAssignmentRequest;
import com.reading.readingappbackend.repository.ClassroomTeacherRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

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
    private final ClassroomTeacherRepository classroomTeacherRepository;

    public TeacherController(SubmissionRepository submissionRepository,
                             SubmissionAnswerRepository submissionAnswerRepository,
                             AssignmentRepository assignmentRepository,
                             QuestionRepository questionRepository,
                             ClassroomRepository classroomRepository,
                             UserRepository userRepository,
                             ClassroomTeacherRepository classroomTeacherRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
        this.assignmentRepository = assignmentRepository;
        this.questionRepository = questionRepository;
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
        this.classroomTeacherRepository = classroomTeacherRepository;
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

        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);

        if (assignmentOptional.isEmpty()) {
            return List.of();
        }

        Assignment assignment = assignmentOptional.get();

        if (assignment.getClassroomId() == null) {
            return List.of();
        }

        // 先校验：这个老师是否有权限看这个班的作业
        Optional<ClassroomTeacher> relationOptional =
                classroomTeacherRepository.findByClassroomIdAndTeacherUsername(
                        assignment.getClassroomId(),
                        teacherUsername
                );

        if (relationOptional.isEmpty()) {
            return List.of();
        }

        // 只查“这个作业所属班级”的学生
        List<User> students = userRepository.findByClassroomId(assignment.getClassroomId());

        // 查这个作业的所有提交
        List<Submission> submissions = submissionRepository.findByAssignmentId(assignmentId);

        Map<String, Submission> submissionMap = new HashMap<>();
        for (Submission submission : submissions) {
            submissionMap.put(submission.getStudentName(), submission);
        }

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
    public List<Assignment> getAssignments(@RequestParam String teacherUsername) {
        List<ClassroomTeacher> teacherRelations =
                classroomTeacherRepository.findByTeacherUsername(teacherUsername);

        if (teacherRelations.isEmpty()) {
            return List.of();
        }

        Set<Long> classroomIds = new HashSet<>();
        for (ClassroomTeacher relation : teacherRelations) {
            classroomIds.add(relation.getClassroomId());
        }

        return assignmentRepository.findByClassroomIdInOrderByDueDateAsc(new ArrayList<>(classroomIds));
    }
    @PostMapping("/assignments")
    public Object createAssignment(@RequestBody CreateTeacherAssignmentRequest request) {

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            return java.util.Map.of("error", "作业标题不能为空");
        }

        if (request.getBookTitle() == null || request.getBookTitle().isBlank()) {
            return java.util.Map.of("error", "书名不能为空");
        }

        if (request.getChapter() == null || request.getChapter().isBlank()) {
            return java.util.Map.of("error", "章节不能为空");
        }

        if (request.getDueDate() == null) {
            return java.util.Map.of("error", "截止日期不能为空");
        }

        if (request.getClassroomId() == null) {
            return java.util.Map.of("error", "班级不能为空");
        }

        if (request.getTeacherUsername() == null || request.getTeacherUsername().isBlank()) {
            return java.util.Map.of("error", "老师用户名不能为空");
        }

        Optional<ClassroomTeacher> relation = classroomTeacherRepository
                .findByClassroomIdAndTeacherUsername(request.getClassroomId(), request.getTeacherUsername());

        if (relation.isEmpty()) {
            return java.util.Map.of("error", "你没有权限给这个班级创建作业");
        }

        Assignment assignment = new Assignment(
                request.getTitle(),
                request.getBookTitle(),
                request.getChapter(),
                request.getDueDate(),
                request.getClassroomId()
        );

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
    @GetMapping("/profile")
    public Object getTeacherProfile(@RequestParam String username) {
        Optional<User> teacherOptional = userRepository.findByUsername(username);

        if (teacherOptional.isEmpty()) {
            return Map.of("error", "老师不存在");
        }

        User teacher = teacherOptional.get();

        if (!"teacher".equals(teacher.getRole())) {
            return Map.of("error", "该账号不是老师");
        }

        List<ClassroomTeacher> relations =
                classroomTeacherRepository.findByTeacherUsername(username);

        List<Map<String, Object>> classrooms = new ArrayList<>();

        for (ClassroomTeacher relation : relations) {
            Optional<Classroom> classroomOptional =
                    classroomRepository.findById(relation.getClassroomId());

            if (classroomOptional.isPresent()) {
                Classroom classroom = classroomOptional.get();

                Map<String, Object> classroomMap = new LinkedHashMap<>();
                classroomMap.put("id", classroom.getId());
                classroomMap.put("name", classroom.getName());

                classrooms.add(classroomMap);
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("username", teacher.getUsername());
        result.put("role", teacher.getRole());
        result.put("displayName", teacher.getDisplayName());
        result.put("email", teacher.getEmail());
        result.put("phone", teacher.getPhone());
        result.put("birthday", teacher.getBirthday());
        result.put("classroomCount", classrooms.size());
        result.put("classrooms", classrooms);

        return result;
    }
    @PutMapping("/profile")
    public Object updateTeacherProfile(@RequestBody Map<String, String> request) {
        String username = request.get("username");

        if (username == null || username.isBlank()) {
            return Map.of("error", "username 不能为空");
        }

        Optional<User> teacherOptional = userRepository.findByUsername(username);

        if (teacherOptional.isEmpty()) {
            return Map.of("error", "老师不存在");
        }

        User teacher = teacherOptional.get();

        if (!"teacher".equals(teacher.getRole())) {
            return Map.of("error", "该账号不是老师");
        }

        teacher.setDisplayName(request.get("displayName"));
        teacher.setEmail(request.get("email"));
        teacher.setPhone(request.get("phone"));
        teacher.setBirthday(request.get("birthday"));

        userRepository.save(teacher);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", "资料更新成功");
        result.put("username", teacher.getUsername());
        result.put("displayName", teacher.getDisplayName());
        result.put("email", teacher.getEmail());
        result.put("phone", teacher.getPhone());
        result.put("birthday", teacher.getBirthday());

        return result;
    }
}