package com.student_portal.demo.controller;

import com.student_portal.demo.entity.Student;
import com.student_portal.demo.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;

    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student());

        return "admin/dashboard";
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
}
