package com.reading.readingappbackend.repository;

import com.reading.readingappbackend.model.ParentStudentLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParentStudentLinkRepository extends JpaRepository<ParentStudentLink, Long> {

    List<ParentStudentLink> findByParentUsername(String parentUsername);

    List<ParentStudentLink> findByStudentUsername(String studentUsername);

    Optional<ParentStudentLink> findByParentUsernameAndStudentUsername(String parentUsername, String studentUsername);
}