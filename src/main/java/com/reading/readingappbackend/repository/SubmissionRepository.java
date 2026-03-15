package com.reading.readingappbackend.repository;

import com.reading.readingappbackend.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByAssignmentId(Long assignmentId);
    List<Submission> findByStudentNameOrderBySubmittedAtDesc(String studentName);
    void deleteByAssignmentId(Long assignmentId);
}