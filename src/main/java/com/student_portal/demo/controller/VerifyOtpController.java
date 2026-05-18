package com.student_portal.demo.controller;


import com.student_portal.demo.entity.User;
import com.student_portal.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class VerifyOtpController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify-otp")
    public String verifyOtpPage() {
        return "verify-otp";
    }


    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam String otp,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        if (!otp.equals(user.getOtp())) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Invalid OTP");

            return "redirect:/verify-otp";
        }

        if (user.getOtpGeneratedTime()
                .plusMinutes(5)
                .isBefore(LocalDateTime.now())) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "OTP expired");

            return "redirect:/verify-otp";
        }

        user.setOtp(null);

        userRepository.save(user);

        return "redirect:/dashboard";
    }
}

