package com.example.vuestagram.security;

import com.example.vuestagram.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityAuthenticationProvider {
    private final JwtUtil jwtUtil;

    // Spring Security에서 사용자의 인증 정보를 담는 객체 생성하는 메소드
    public Authentication authenticate(String token) {
        // 파라미터 3개를 받는데, 첫번째는 principal, 두번째 credentials, 세번째 authorities
        // 인증된 사용자 객체 - 유저 모델 or 토큰의 payload
        // 비밀번호 저장 여부 - 대부분은 비밀번호 저장하지 않아서 null로 설정
        // 사용자 권한 목록 - 유저의 권한에 따라 접근 제어
        return new UsernamePasswordAuthenticationToken(jwtUtil.getClaims(token), null, null);
    }
}
