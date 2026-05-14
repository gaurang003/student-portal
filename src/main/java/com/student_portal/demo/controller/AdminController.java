package com.student_portal.demo.controller;

import com.student_portal.demo.entity.Student;
import com.student_portal.demo.repository.CourseRepository;
import com.student_portal.demo.repository.StudentRepository;
import com.student_portal.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/save-student")
    public String saveStudent(@ModelAttribute Student student) {

        studentService.save(student);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {

        model.addAttribute("student", studentService.getStudent(id));

        return "admin/edit-student";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student) {

        studentService.save(student);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {

        studentService.delete(id);

        return "redirect:/admin/dashboard";
    }

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

}
