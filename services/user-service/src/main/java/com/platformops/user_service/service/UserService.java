package com.platformops.user_service.service;


import com.platformops.user_service.dto.RegisterRequest;
import com.platformops.user_service.dto.UpdateUserRequest;
import com.platformops.user_service.model.User;
import com.platformops.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public User updateUser(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(request.getName());
            existingUser.setEmail(request.getEmail());
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}