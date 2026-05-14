package com.student_portal.demo.entity;

import jakarta.persistence.*;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private int marks;

    private String grade;

    @ManyToOne
    private Student student;

    // Getters and Setters
}