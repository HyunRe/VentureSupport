package com.example.app.service;

import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User updatedUser) {
        try {
            User existingUser = getUserById(id);
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setLat(updatedUser.getLat());
            existingUser.setLng(updatedUser.getLng());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setPassword(updatedUser.getPassword());
            return userRepository.save(existingUser);
        } catch (Exception e) {
            // 로깅
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating user", e);
        }
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

}
