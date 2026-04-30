package com.reading.readingappbackend.controller;

import com.reading.readingappbackend.model.LoginRequest;
import com.reading.readingappbackend.model.LoginResponse;
import com.reading.readingappbackend.model.RegisterRequest;
import com.reading.readingappbackend.model.ResetPasswordBySmsRequest;
import com.reading.readingappbackend.model.SendSmsCodeRequest;
import com.reading.readingappbackend.model.SmsLoginRequest;
import com.reading.readingappbackend.model.User;
import com.reading.readingappbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final Map<String, String> smsCodeStore = new HashMap<>();
    private final Map<String, Long> smsCodeTimeStore = new HashMap<>();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String generateUsername(String role) {
        String prefix;

        switch (role) {
            case "student":
                prefix = "stu";
                break;
            case "teacher":
                prefix = "tea";
                break;
            case "parent":
                prefix = "par";
                break;
            default:
                prefix = "usr";
                break;
        }

        String username;

        do {
            int randomNumber = (int) ((Math.random() * 90000000) + 10000000);
            username = prefix + randomNumber;
        } while (userRepository.existsByUsername(username));

        return username;
    }

    @PostMapping("/register")
    public Object register(@RequestBody RegisterRequest request) {

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return Map.of("error", "password 不能为空");
        }

        if (request.getRole() == null || request.getRole().isBlank()) {
            return Map.of("error", "role 不能为空");
        }

        String linkedStudentUsername = null;
        String generatedUsername = generateUsername(request.getRole());

        User user = new User(
                generatedUsername,
                request.getPassword(),
                request.getRole(),
                linkedStudentUsername
        );

        user.setPhone(request.getPhone());

        User saved = userRepository.save(user);

        return new LoginResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getRole(),
                saved.getLinkedStudentUsername(),
                saved.getPhone()
        );
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return Map.of("error", "账号不能为空");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return Map.of("error", "密码不能为空");
        }

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
                user.getLinkedStudentUsername(),
                user.getPhone()
        );
    }

    @PostMapping("/send-sms-code")
    public Object sendSmsCode(@RequestBody SendSmsCodeRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return Map.of("error", "账号不能为空");
        }

        if (request.getPhone() == null || request.getPhone().isBlank()) {
            return Map.of("error", "phone 不能为空");
        }

        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return Map.of("error", "用户不存在");
        }

        User user = optionalUser.get();

        if (user.getPhone() == null || user.getPhone().isBlank()) {
            return Map.of("error", "该账号还没有绑定手机号");
        }

        if (!request.getPhone().equals(user.getPhone())) {
            return Map.of("error", "手机号与账号不匹配");
        }

        String code = String.valueOf((int) ((Math.random() * 900000) + 100000));

        String key = request.getUsername() + "|" + request.getPhone();

        smsCodeStore.put(key, code);
        smsCodeTimeStore.put(key, System.currentTimeMillis());

        return Map.of(
                "message", "验证码已生成（当前为测试模式）",
                "code", code
        );
    }

    @PostMapping("/login-by-sms")
    public Object loginBySms(@RequestBody SmsLoginRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return Map.of("error", "账号不能为空");
        }

        if (request.getPhone() == null || request.getPhone().isBlank()) {
            return Map.of("error", "phone 不能为空");
        }

        if (request.getCode() == null || request.getCode().isBlank()) {
            return Map.of("error", "code 不能为空");
        }

        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return Map.of("error", "用户不存在");
        }

        User user = optionalUser.get();

        if (user.getPhone() == null || user.getPhone().isBlank()) {
            return Map.of("error", "该账号还没有绑定手机号");
        }

        if (!request.getPhone().equals(user.getPhone())) {
            return Map.of("error", "手机号与账号不匹配");
        }

        String key = request.getUsername() + "|" + request.getPhone();

        String savedCode = smsCodeStore.get(key);
        Long savedTime = smsCodeTimeStore.get(key);

        if (savedCode == null || savedTime == null) {
            return Map.of("error", "请先获取验证码");
        }

        if (System.currentTimeMillis() - savedTime > 5 * 60 * 1000) {
            smsCodeStore.remove(key);
            smsCodeTimeStore.remove(key);
            return Map.of("error", "验证码已过期");
        }

        if (!request.getCode().equals(savedCode)) {
            return Map.of("error", "验证码错误");
        }

        smsCodeStore.remove(key);
        smsCodeTimeStore.remove(key);

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getLinkedStudentUsername(),
                user.getPhone()
        );
    }

    @PostMapping("/reset-password-by-sms")
    public Object resetPasswordBySms(@RequestBody ResetPasswordBySmsRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return Map.of("error", "账号不能为空");
        }

        if (request.getPhone() == null || request.getPhone().isBlank()) {
            return Map.of("error", "phone 不能为空");
        }

        if (request.getCode() == null || request.getCode().isBlank()) {
            return Map.of("error", "code 不能为空");
        }

        if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
            return Map.of("error", "newPassword 不能为空");
        }

        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return Map.of("error", "用户不存在");
        }

        User user = optionalUser.get();

        if (user.getPhone() == null || user.getPhone().isBlank()) {
            return Map.of("error", "该账号还没有绑定手机号");
        }

        if (!request.getPhone().equals(user.getPhone())) {
            return Map.of("error", "手机号与账号不匹配");
        }

        String key = request.getUsername() + "|" + request.getPhone();

        String savedCode = smsCodeStore.get(key);
        Long savedTime = smsCodeTimeStore.get(key);

        if (savedCode == null || savedTime == null) {
            return Map.of("error", "请先获取验证码");
        }

        if (System.currentTimeMillis() - savedTime > 5 * 60 * 1000) {
            smsCodeStore.remove(key);
            smsCodeTimeStore.remove(key);
            return Map.of("error", "验证码已过期");
        }

        if (!request.getCode().equals(savedCode)) {
            return Map.of("error", "验证码错误");
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        smsCodeStore.remove(key);
        smsCodeTimeStore.remove(key);

        return Map.of("message", "密码重置成功");
    }
}