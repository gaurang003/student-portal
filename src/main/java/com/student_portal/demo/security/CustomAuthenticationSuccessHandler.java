package com.student_portal.demo.security;

import com.student_portal.demo.entity.User;
import com.student_portal.demo.repository.UserRepository;
import com.student_portal.demo.service.EmailService;
import com.student_portal.demo.util.OtpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if (user.isTwoFactorEnabled()) {

            String otp = OtpUtil.generateOtp();

            user.setOtp(otp);

            user.setOtpGeneratedTime(LocalDateTime.now());

            userRepository.save(user);

            emailService.sendEmail(
                    user.getEmail(),
                    "OTP Verification",
                    "Your OTP is: " + otp
            );

            response.sendRedirect("/verify-otp");

        } else {

            response.sendRedirect("/dashboard");
        }
    }
}