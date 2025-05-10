package com.backend.movie_res_system.controller;

import com.backend.movie_res_system.entity.User;
import com.backend.movie_res_system.service.AuthService;
import com.backend.movie_res_system.util.UserLoginRequest;
import com.backend.movie_res_system.util.UserRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterRequest request) {
        User result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public  ResponseEntity<String> login(@RequestBody UserLoginRequest request) {
        String result = authService.login(request);
        return ResponseEntity.ok(result);
    }
}
