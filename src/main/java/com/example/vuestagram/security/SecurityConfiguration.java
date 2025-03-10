package com.example.vuestagram.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// 스프링 시큐리티 설정을 활성화 하는 어노테이션.
// 5.7 버전 이상일 때 생략할 수 있다.
// 5.6 버전 이하일 때 작성하지 않으면 오류가 나타난다.

// 설정과 관련된 클래스
public class SecurityConfiguration {
    @Bean // Bean 메소드, 객체는 우리가 관리하는 것이 아닌 spring boot가 알아서 해줌
    // Bean을 사용하기 위해서 등록하는 것이 @Bean이다?

    // 비밀번호를 암호화 관련 구현체 정의 및 빈 등록 하는 메소드?
    // 비밀번호 일치 여부도 확인할 수 있다
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // 세션 비활성화 - 람다식 작성
                .httpBasic(h -> h.disable()) // SSR이 아니므로 화면 생성 비활성화 설정
                .formLogin(form -> form.disable()) // SSR이 아니므로 폼 로그인 기능 비활성 설정
                // CSRF 토큰 비활성화
                .csrf(csrf -> csrf.disable()) // SSR이 아니므로 CSRF 토큰 인증 비활성 설정

                .authorizeHttpRequests(request -> // 리퀘스트에 대한 인가 체크 처리
                    request.requestMatchers("/api/login").permitAll() // 로그인을 하지 않은 모두에게 허용함. 인가 없이 접근 가능함
//                            .requestMatchers("/api/registration").permitAll()
                            .anyRequest().authenticated() // 위 정의한 것들 이외에는 인증(인가)을 필요로 하게 만든다.
                )
                .build();
    }
}
