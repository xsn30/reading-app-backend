package com.reading.readingappbackend.repository;

import com.reading.readingappbackend.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // 按书名模糊查询（不区分大小写）
    Page<Assignment> findByBookTitleContainingIgnoreCase(String bookTitle, Pageable pageable);
    List<Assignment> findByClassroomIdOrderByDueDateAsc(Long classroomId);

    List<Assignment> findByClassroomIdInOrderByDueDateAsc(List<Long> classroomIds);
}