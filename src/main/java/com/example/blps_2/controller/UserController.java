package com.example.blps_2.controller;

import com.example.blps_2.model.User;
import com.example.blps_2.service.UserService;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, String> signIn(@Valid @RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public Map<String, String> signUp(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/admin")
    public Map<String, String> signUpAdmin(@Valid @RequestBody User user) {
        return userService.saveAdmin(user);
    }
}

