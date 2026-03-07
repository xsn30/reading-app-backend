package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.LoginRequest;
import com.reading.readingappbackend.model.LoginResponse;
import com.reading.readingappbackend.model.RegisterRequest;
import com.reading.readingappbackend.model.User;
import com.reading.readingappbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public Object register(@RequestBody RegisterRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return Map.of("error", "username 不能为空");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return Map.of("error", "password 不能为空");
        }

        if (request.getRole() == null || request.getRole().isBlank()) {
            return Map.of("error", "role 不能为空");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            return Map.of("error", "用户名已存在");
        }

        String linkedStudentUsername = request.getLinkedStudentUsername();

        if ("parent".equals(request.getRole())) {

            if (linkedStudentUsername == null || linkedStudentUsername.isBlank()) {
                return Map.of("error", "家长注册时必须填写孩子用户名");
            }

            Optional<User> studentOptional = userRepository.findByUsername(linkedStudentUsername);

            if (studentOptional.isEmpty()) {
                return Map.of("error", "绑定失败：学生账号不存在");
            }

            User studentUser = studentOptional.get();

            if (!"student".equals(studentUser.getRole())) {
                return Map.of("error", "绑定失败：该账号不是学生");
            }
        }

        User user = new User(
                request.getUsername(),
                request.getPassword(),
                request.getRole(),
                linkedStudentUsername
        );

        User saved = userRepository.save(user);

        return new LoginResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getRole(),
                saved.getLinkedStudentUsername()
        );
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return Map.of("error", "用户不存在");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return Map.of("error", "密码错误");
        }

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getLinkedStudentUsername()
        );
    }
}