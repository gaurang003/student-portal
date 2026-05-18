package com.student_portal.demo.service;

public interface EmailService {

    void sendEmail(String to,
                   String subject,
                   String body);
}