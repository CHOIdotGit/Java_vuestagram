package com.example.vuestagram.util.jwt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor // 상수 - 생성자. 생성자를 자동으로 만들어주는 어노테이션
@ConfigurationProperties(prefix = "config.jwt") // prefix에는 yml 파일에 작성한 것을 넣어주면 된다?
public class JwtConfig {
    private final String issuer; // 토큰을 발급하는 사람이 누구인지 관리? 회사의 이메일
    private final String type; // 토큰의 타입. 지금은 JWT
    private final int accessTokenExpiry; // 에세스 토큰 유효 시간. 밀리세컨드 단위
    private final int refreshTokenExpiry; // 리프레시 토큰 유효 시간. 일반적으로 유저한테 주지 않음
    private final String refreshTokenCookieName; //
    private final int refreshTokenCookieExpiry; // 토큰 시간 설정?
    private final String secret; // 암호화. 번호.
    private final String headerKey; // 엑세스 토큰을 프론트에서 전달해 올 때, 해더에 authorization이라는 키가 들어갈 프로퍼티
    private final String scheme; // 엑세스 토큰 맨 앞에 특정 글자, bearer라는 문자열이 들어갈 예정
    private final String reissUri;
}
