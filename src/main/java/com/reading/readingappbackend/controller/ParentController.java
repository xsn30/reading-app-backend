package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Submission;
import com.reading.readingappbackend.model.SubmissionAnswer;
import com.reading.readingappbackend.repository.SubmissionAnswerRepository;
import com.reading.readingappbackend.repository.SubmissionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.reading.readingappbackend.model.ParentLearningSummary;
import com.reading.readingappbackend.model.LinkStudentRequest;
import com.reading.readingappbackend.model.ParentStudentInfo;
import com.reading.readingappbackend.model.ParentStudentLink;
import com.reading.readingappbackend.model.User;
import com.reading.readingappbackend.repository.ParentStudentLinkRepository;
import com.reading.readingappbackend.repository.UserRepository;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/parent")
public class ParentController {

    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;
    private final ParentStudentLinkRepository parentStudentLinkRepository;
    private final UserRepository userRepository;

    public ParentController(SubmissionRepository submissionRepository,
                            SubmissionAnswerRepository submissionAnswerRepository,
                            ParentStudentLinkRepository parentStudentLinkRepository,
                            UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.submissionAnswerRepository = submissionAnswerRepository;
        this.parentStudentLinkRepository = parentStudentLinkRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/students/{studentName}/submissions")
    public List<Submission> getStudentSubmissions(@PathVariable String studentName) {
        return submissionRepository.findByStudentNameOrderBySubmittedAtDesc(studentName);
    }

    @GetMapping("/submissions/{submissionId}")
    public List<SubmissionAnswer> getSubmissionAnswers(@PathVariable Long submissionId) {
        return submissionAnswerRepository.findBySubmissionId(submissionId);
    }
    @GetMapping("/students/{studentUsername}/summary")
    public ParentLearningSummary getStudentLearningSummary(@PathVariable String studentUsername) {
        List<Submission> submissions =
                submissionRepository.findByStudentNameOrderBySubmittedAtDesc(studentUsername);

        if (submissions.isEmpty()) {
            return new ParentLearningSummary(0, 0.0, null, null, null);
        }

        // 每个作业只保留“最后一次提交”
        Map<Long, Submission> latestByAssignment = new LinkedHashMap<>();

        for (Submission submission : submissions) {
            Long assignmentId = submission.getAssignmentId();

            // 因为 submissions 已经按 submittedAt DESC 排序，
            // 所以第一次放进去的就是这个作业最新的一次提交
            if (!latestByAssignment.containsKey(assignmentId)) {
                latestByAssignment.put(assignmentId, submission);
            }
        }

        List<Submission> effectiveSubmissions = new ArrayList<>(latestByAssignment.values());

        int totalAssignments = effectiveSubmissions.size();

        double averageScore = effectiveSubmissions.stream()
                .mapToInt(Submission::getTotalScore)
                .average()
                .orElse(0.0);

        // 最近一次提交仍然按所有 submission 里最新的那条
        Submission latest = submissions.get(0);

        return new ParentLearningSummary(
                totalAssignments,
                averageScore,
                latest.getTotalScore(),
                latest.getMaxScore(),
                latest.getSubmittedAt()
        );
    }
    @PostMapping("/link-student")
    public Object linkStudent(@RequestBody LinkStudentRequest request) {

        if (request.getParentUsername() == null || request.getParentUsername().isBlank()) {
            return Map.of("error", "家长用户名不能为空");
        }

        if (request.getStudentUsername() == null || request.getStudentUsername().isBlank()) {
            return Map.of("error", "孩子用户名不能为空");
        }

        Optional<User> parentOptional = userRepository.findByUsername(request.getParentUsername());
        if (parentOptional.isEmpty()) {
            return Map.of("error", "家长账号不存在");
        }

        User parent = parentOptional.get();
        if (!"parent".equals(parent.getRole())) {
            return Map.of("error", "该账号不是家长");
        }

        Optional<User> studentOptional = userRepository.findByUsername(request.getStudentUsername());
        if (studentOptional.isEmpty()) {
            return Map.of("error", "学生账号不存在");
        }

        User student = studentOptional.get();
        if (!"student".equals(student.getRole())) {
            return Map.of("error", "该账号不是学生");
        }

        Optional<ParentStudentLink> existing =
                parentStudentLinkRepository.findByParentUsernameAndStudentUsername(
                        request.getParentUsername(),
                        request.getStudentUsername()
                );

        if (existing.isPresent()) {
            return Map.of("error", "该孩子已绑定");
        }

        ParentStudentLink link = new ParentStudentLink(
                request.getParentUsername(),
                request.getStudentUsername()
        );

        parentStudentLinkRepository.save(link);

        return Map.of(
                "message", "绑定成功",
                "parentUsername", request.getParentUsername(),
                "studentUsername", request.getStudentUsername()
        );
    }
    @GetMapping("/{parentUsername}/children")
    public List<ParentStudentInfo> getChildren(@PathVariable String parentUsername) {
        List<ParentStudentLink> links = parentStudentLinkRepository.findByParentUsername(parentUsername);

        List<ParentStudentInfo> result = new ArrayList<>();

        for (ParentStudentLink link : links) {
            Optional<User> studentOptional = userRepository.findByUsername(link.getStudentUsername());
            if (studentOptional.isPresent()) {
                User student = studentOptional.get();
                result.add(new ParentStudentInfo(student.getId(), student.getUsername()));
            }
        }

        return result;
    }
}