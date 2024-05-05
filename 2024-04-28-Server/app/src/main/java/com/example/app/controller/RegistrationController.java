package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // Kiểm tra xem người dùng đã tồn tại chưa
        if(userRepository.existsById(user.getUserId())) {
            return "User already exists!";
        }

        // Lưu người dùng mới vào cơ sở dữ liệu
        userRepository.save(user);
        return "User registered successfully!";
    }
}
