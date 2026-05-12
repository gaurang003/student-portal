package com.student_portal.demo;

import com.student_portal.demo.entity.User;
import com.student_portal.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository,
						  PasswordEncoder passwordEncoder) {

		return args -> {

			if (userRepository.findByEmail("admin") == null) {

				User admin = new User();

				admin.setName("Administrator");

				admin.setEmail("admin");

				admin.setPassword(
						passwordEncoder.encode("admin")
				);

				admin.setRole("ADMIN");

				userRepository.save(admin);

				System.out.println("Admin Created");
			}
		};
	}
}
