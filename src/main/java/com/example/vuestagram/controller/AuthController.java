package com.example.vuestagram.controller;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.service.AuthService;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // REST API
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    // private final JwtConfig jwtConfig;
    private final AuthService authService;

    @PostMapping("login") // get는 GetMapping을 사용하면 된다.
    // 위에 import가 되어야 한다.
    public String login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        // return jwtConfig.getIssuer(); // return authService.login();
        // return  loginRequestDTO.getAccount() + ":" + loginRequestDTO.getPassword();
        return authService.login(loginRequestDTO);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
