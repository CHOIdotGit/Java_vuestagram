package com.example.vuestagram.controller;

import com.example.vuestagram.service.AuthService;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST API
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
//    private final JwtConfig jwtConfig;
    private final AuthService authService;

    @PostMapping("login") // get는 GetMapping을 사용하면 된다.
    // 위에 import가 되어야 한다.
    public String login() {
//        return jwtConfig.getIssuer();
        return authService.login();
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
