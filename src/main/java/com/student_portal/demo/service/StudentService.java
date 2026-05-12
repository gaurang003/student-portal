package com.student_portal.demo.service;

import com.student_portal.demo.entity.Student;
import com.student_portal.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
