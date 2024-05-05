package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User existingUserByUsername = userRepository.findByUserName(user.getUserName());
        User existingUserByEmail = userRepository.findByEmail(user.getEmail());

        if (existingUserByUsername != null && user.getPassword().equals(existingUserByUsername.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        } else if (existingUserByEmail != null && user.getPassword().equals(existingUserByEmail.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }
    }
}
