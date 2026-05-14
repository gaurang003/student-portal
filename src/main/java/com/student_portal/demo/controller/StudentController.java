package com.student_portal.demo.controller;

import com.student_portal.demo.entity.Student;
import com.student_portal.demo.repository.CourseRepository;
import com.student_portal.demo.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // STUDENT DASHBOARD
    @GetMapping("/student/dashboard")
    public String studentDashboard() {
        return "student/dashboard";
    }

    // STUDENT PROFILE
    @GetMapping("/student/profile")
    public String profile(Model model, Authentication auth) {

        Student student =
                studentRepository.findByEmail(auth.getName());

        model.addAttribute("student", student);

        return "student/profile";
    }

    // ADMIN DASHBOARD
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        model.addAttribute(
                "studentCount",
                studentRepository.count()
        );

        model.addAttribute(
                "courseCount",
                courseRepository.count()
        );

        return "admin/dashboard";
    }

    // SEARCH + PAGINATION
    @GetMapping("/students")
    public String students(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {

        Pageable pageable = PageRequest.of(page, 5);

        Page<Student> students =
                studentRepository.findByNameContaining(
                        keyword,
                        pageable
                );

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);

        return "student/list";
    }


    // OPEN ADD FORM
    @GetMapping("/students/add")
    public String addStudentPage(Model model) {

        model.addAttribute(
                "student",
                new Student()
        );

        return "student/add";
    }

    // SAVE STUDENT
    @PostMapping("/students/save")
    public String saveStudent(
            @ModelAttribute Student student
    ) {

        studentRepository.save(student);

        return "redirect:/students";
    }

}