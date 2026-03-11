package com.reading.readingappbackend.repository;

import com.reading.readingappbackend.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findByTeacherUsername(String teacherUsername);

}