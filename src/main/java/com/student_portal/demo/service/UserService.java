package com.student_portal.demo.service;

import com.student_portal.demo.dto.RegisterDto;
import com.student_portal.demo.entity.User;

public interface UserService {
    void register(User user);

    void register(RegisterDto dto);
}
