package com.student_portal.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate attendanceDate;

    private String status;

    @ManyToOne
    private Student student;

    // Getters and Setters
}