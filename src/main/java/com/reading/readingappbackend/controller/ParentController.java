package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.Submission;
import com.reading.readingappbackend.model.SubmissionAnswer;
import com.reading.readingappbackend.repository.SubmissionAnswerRepository;
import com.reading.readingappbackend.repository.SubmissionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.reading.readingappbackend.model.ParentLearningSummary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}