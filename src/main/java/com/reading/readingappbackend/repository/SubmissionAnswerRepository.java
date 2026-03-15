package com.reading.readingappbackend.repository;

import com.reading.readingappbackend.model.SubmissionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionAnswerRepository extends JpaRepository<SubmissionAnswer, Long> {
    List<SubmissionAnswer> findBySubmissionId(Long submissionId);
    void deleteBySubmissionId(Long submissionId);
}