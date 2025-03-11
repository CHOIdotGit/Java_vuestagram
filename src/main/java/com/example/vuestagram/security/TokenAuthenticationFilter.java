package com.example.vuestagram.security;

import com.example.vuestagram.util.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // 자동으로 bin을 등록하도록 처리하는 어노테이션
@RequiredArgsConstructor // final로 선언된 객체를 생성. 생성자를 작성할 필요 없게 만들어 준다.
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    // 상속 받은 곳에 최초에 한 번 실행된다.
    private final JwtUtil jwtUtil; // 상수 - 변하지 않는 값 / 생성자에서만 초기화 가능
    private final SecurityAuthenticationProvider securityAuthenticationProvider; // D.I
    
    @Override
    // 엑세스 토큰의 유효 여부를 확인하고 인증 정볼르 스프링 시큐리티에 설정하는 메소드
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 쿠키에서 토큰 획득
        String token = jwtUtil.getAccessTokenInCookie(request);

        if(token != null) {
            try {
                // Security 인증 정보 설정 // SecurityContextHolder - 인증에 관한 모든 정보를 가지고 있음
                SecurityContextHolder.getContext().setAuthentication(securityAuthenticationProvider.authenticate(token));
            } catch(Exception e) {
                throw new RuntimeException("사용할 수 없는 토큰입니다.");
            }
        }

        filterChain.doFilter(request, response); // 다음 필터 호출
    }

//    public TokenAuthenticationFilter(JwtUtil jwtUtil) { // << D.I - 의존성 주입
//        this.jwtUtil = jwtUtil;
//    }
}
