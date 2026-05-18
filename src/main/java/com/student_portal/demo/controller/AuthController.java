package com.student_portal.demo.controller;

import com.student_portal.demo.dto.ForgotPasswordDto;
import com.student_portal.demo.dto.ResetPasswordDto;
import com.student_portal.demo.entity.User;
import com.student_portal.demo.repository.UserRepository;
import com.student_portal.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ) {

        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            return "redirect:/register?error";
        }

        User user = new User();

        user.setName(name);

        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setRole("STUDENT");

        userRepository.save(user);

        return "redirect:/login";
    }
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/student/dashboard";
    }

    // PROFILE PAGE
    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName());

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model) {

        model.addAttribute("forgotPasswordDto",
                new ForgotPasswordDto());

        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @ModelAttribute ForgotPasswordDto dto,
            RedirectAttributes redirectAttributes) {

        User user = userRepository.findByEmail(dto.getEmail());

        if (user == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Email not found");

            return "redirect:/forgot-password";
        }

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);

        userRepository.save(user);

        String resetLink =
                "http://localhost:8001/reset-password?token="
                        + token;

        emailService.sendEmail(
                user.getEmail(),
                "Password Reset",
                "Click below link to reset password:\n"
                        + resetLink
        );

        redirectAttributes.addFlashAttribute(
                "success",
                "Reset link sent to email");

        return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(
            @RequestParam("token") String token,
            Model model) {

        User user = userRepository.findByResetToken(token);

        if (user == null) {
            return "redirect:/login?invalidToken";
        }

        model.addAttribute("token", token);

        model.addAttribute("resetPasswordDto",
                new ResetPasswordDto());

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("token") String token,
            @ModelAttribute ResetPasswordDto dto,
            RedirectAttributes redirectAttributes) {

        User user = userRepository.findByResetToken(token);

        if (user == null) {
            return "redirect:/login?invalidToken";
        }

        if (!dto.getPassword()
                .equals(dto.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Passwords do not match");

            return "redirect:/reset-password?token=" + token;
        }

        user.setPassword(
                passwordEncoder.encode(dto.getPassword()));

        user.setResetToken(null);

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "success",
                "Password updated successfully");

        return "redirect:/login";
    }


}