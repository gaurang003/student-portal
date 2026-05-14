package com.student_portal.demo.controller;

import com.student_portal.demo.entity.Course;
import com.student_portal.demo.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", service.getAll());
        return "course/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Course course) {
        service.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/courses";
    }
}