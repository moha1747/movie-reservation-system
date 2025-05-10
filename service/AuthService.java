package com.backend.movie_res_system.service;

import com.backend.movie_res_system.entity.User;
import com.backend.movie_res_system.util.*;
import com.backend.movie_res_system.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;


@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email address is required to register");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("password is required to register");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required to register");
        }
        try {
            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("User already exists, please login");
            } else {
                String hashedPassword = passwordEncoder.encode(request.getPassword());
                User user = new User(request.getName(), request.getEmail(), hashedPassword);
                userRepository.save(user);
                return user;
            }
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());

        }

    }

    public String login(UserLoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email address is required to register");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("password is required to register");
        }
        try {
            Optional<User> userLookup = userRepository.findByEmail(request.getEmail());
            if (userLookup.isEmpty()) {
                throw new IllegalArgumentException("User does not exist, please register");
            }

            User user = userLookup.get();
            boolean passwordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
            if (!passwordMatch) {
                throw new IllegalArgumentException("Invalid email or password");
            }

            return "Login successful";

        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}

