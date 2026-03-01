package com.reading.readingappbackend.repository;

import com.reading.readingappbackend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByAssignmentId(Long assignmentId);
    void deleteByAssignmentId(Long assignmentId);
}