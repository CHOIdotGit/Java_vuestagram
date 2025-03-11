package com.example.vuestagram.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {
    // IDE - Refactor 기능을 사용하면 편리하다.
    // Request Header에서 특정 쿠키를 획득하는 메소드
    public Cookie getCookie(HttpServletRequest request, String name) {
//        try {
//            Cookie[] cookies = request.getCookies();
//            if(cookies != null) {
//                Optional.ofNullable(cookies);
//            }
//        } catch (RuntimeException e) {
//            throw new RuntimeException();
//        }
        // 해당 쿠키가 없다고 가정할 때의 경우 - null 허용하는 옵셔널 객체 -> String을 통해 Array로 만들 예정
        // 성공적으로 쿠키를 가져오지 못한 경우, ofNullable을 사용해서 null을 가질 수 있는 Optional을 생성
        // orElseThrow메소드 사용
        return Arrays.stream(Optional.ofNullable(request.getCookies())
                        .orElseThrow(() -> new RuntimeException("쿠키를 찾을 수 없습니다.")))
        // Stream<Cookie[]> 생성 // 위 코드를 사용하는 이유는 특정 값을 가져오고 싶어서임.
                .filter(item -> item.getName().equals(name)) // 필터 조건에 맞는 Stream을 리턴(중간 연산)
                .findFirst() // 필터의 조건에 맞는 가장 첫 번째 아이템을 선택해서 Optional을 리턴(Stream의 최종 연산)
                .orElseThrow(() -> new RuntimeException("쿠키를 찾을 수 없습니다."));
        // 이중 try-catch문을 사용해야 하지만 자바에서 위와 같이 사용할 수 있다.
        // 우리가 의도한 대로 동작하지 않고 시스템이 죽어버릴 수 있으므로 예외관리를 사전에 해 두어 꼼꼼하게 관리하면 문제 파악이 쉽다.
    }

    // Response Header에 쿠키 설정하는 메소드 - 리턴 값이 필요하지 않음
    public void setCookie(HttpServletResponse response, String name, String value, int expiry, String domain) {
        // 특정 상황에서만 쿠키를 받아오고 싶을 때 domain을 사용한다.
        Cookie cookie = new Cookie(name, value); // 이름과 값을 순서대로 넣는다면 그 값에 해당하는 cookie 객체가 만들어진다?
        cookie.setPath(domain); // 문자열로 경로를 작성하면 됨. 특정 요청으로 들어올 때 쿠키를 넘겨주도록 설정
        cookie.setMaxAge(expiry); // 쿠키 만료 시간 설정
        cookie.setHttpOnly(true); // 보안 쿠키 설정, 프론트에서 자바스크립트로 쿠키 획득이 불가능해진다.
        response.addCookie(cookie); // 설정된 쿠키를 response에 추가
    }
}
