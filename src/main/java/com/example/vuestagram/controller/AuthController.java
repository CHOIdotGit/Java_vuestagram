package com.example.vuestagram.controller;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.dto.response.ResponseBase;
import com.example.vuestagram.dto.response.ResponseLogin;
import com.example.vuestagram.service.AuthService;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // REST API
@RequestMapping// ("/api")
@RequiredArgsConstructor
public class AuthController {
    // private final JwtConfig jwtConfig;
    private final AuthService authService;

    @PostMapping("login") // get는 GetMapping을 사용하면 된다.
    // 위에 import가 되어야 한다.
    public ResponseEntity<ResponseBase<ResponseLogin>> login(
            HttpServletResponse response,
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        // return jwtConfig.getIssuer(); // return authService.login();
        // return  loginRequestDTO.getAccount() + ":" + loginRequestDTO.getPassword();
        ResponseLogin responseLogin = authService.login(loginRequestDTO, response);

        ResponseBase<ResponseLogin> responseBase =
                ResponseBase.<ResponseLogin>builder()
                        .status(200)
                        .message("로그인 성공")
                        .data(responseLogin)
                        .build();

        //  authService.login(loginRequestDTO, response);
        return ResponseEntity.status(200).body(responseBase);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
