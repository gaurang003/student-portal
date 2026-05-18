package com.student_portal.demo.repository;

import com.student_portal.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByResetToken(String token);

}
