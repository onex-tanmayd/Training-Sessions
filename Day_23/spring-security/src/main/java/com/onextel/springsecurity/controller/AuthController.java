package com.onextel.springsecurity.controller;

import com.onextel.springsecurity.model.User;
import com.onextel.springsecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // hash pwd
        userRepo.save(user); // save to DB
        return "User " + user.getUsername() + " registered!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, authenticated user!";
    }
}
