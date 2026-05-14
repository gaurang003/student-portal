package com.student_portal.demo.repository;

import com.student_portal.demo.entity.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    Page<Student> findByNameContaining(
            String keyword,
            Pageable pageable
    );
}