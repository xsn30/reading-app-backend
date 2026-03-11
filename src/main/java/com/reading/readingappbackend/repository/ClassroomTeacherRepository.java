package com.reading.readingappbackend.repository;

import com.reading.readingappbackend.model.ClassroomTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassroomTeacherRepository extends JpaRepository<ClassroomTeacher, Long> {

    List<ClassroomTeacher> findByClassroomId(Long classroomId);

    List<ClassroomTeacher> findByTeacherUsername(String teacherUsername);

    Optional<ClassroomTeacher> findByClassroomIdAndTeacherUsername(Long classroomId, String teacherUsername);
}