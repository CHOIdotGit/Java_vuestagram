package com.example.vuestagram.service;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.model.User;
import com.example.vuestagram.repository.UserRepository;
import com.example.vuestagram.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService{
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequestDTO loginRequestDTO) {
        // User user = new User();
        // user.setUserId(2L);
        // Optional<User> result = userRepository.findById(9L);
        Optional<User> result = userRepository.findByAccount(loginRequestDTO.getAccount());

        // 유저 존재 여부 체크
        // if(result == null) {
        if(result.isEmpty()) {
            throw new RuntimeException("존재하지 않는 유저입니다."); // 내부 로직 상에서 처리하고 싶을 때 Runtime 사용
        }

        // 비밀번호 체크
        if(!(passwordEncoder.matches(loginRequestDTO.getPassword(), result.get().getPassword()))) {
            // 1. 유저가 보낸 패스워드 2. 검색해서 가져온 패스워드
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        // 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(result.get()); // result.get() -> user
        String refreshToken = jwtUtil.generateRefreshToken(result.get()); // user

        return accessToken + " || " + refreshToken;
        // 컨트롤러에서 로그인 메소드 호출
    }
}
