package com.student_portal.demo.config;

import com.student_portal.demo.entity.User;
import com.student_portal.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdmin() {

        User admin = userRepository.findByEmail("admin");

        if (admin == null) {

            User user = new User();

            user.setEmail("admin");

            user.setPassword(
                    passwordEncoder.encode("admin")
            );

            user.setRole("ADMIN");

            userRepository.save(user);

            System.out.println("Admin Created");
        }
    }
}
