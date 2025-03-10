package com.example.vuestagram.util.jwt;

import com.example.vuestagram.model.User;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component // 빈을 등록하는 것은 같으나 클래스 레벨에서 사용한다.
public class JwtUtil {
//    토큰 생성, 획득, 테이블 가져오는 토큰과 관련된 작업 유틸 클래스
//    엑세스, 리프레시 토큰을 만드는 것은 같으나 유효시간의 차이가 있음
    private final JwtConfig jwtConfig; //<< 얘가 프로퍼티임
    private final SecretKey secretKey; // JJWT는 설정한 secret이 아닌 base64로 인코딩된 것을 사용한다. 사용할 때 디코딩 하여서 사용한다.

    public JwtUtil(JwtConfig jwtConfig) { // 별도로 만들었음.
        this.jwtConfig = jwtConfig;

        // Base64 인코딩 된 Secret을 디코딩 하여 Key 객체로 변환한다.
        // HMAC - 암호화된 서명에 필요한 적절한 SecretKey를 제공하기 위한 것
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret())); // 디코드 처리

    }

    // 엑세스 토큰 생성 메소드
    public String generateAccessToken(User user) {
        return this.generateToken(user.getUserId(), jwtConfig.getAccessTokenExpiry());
        // 첫 번째 토큰은 유저의 아이디, 두 번째 파라미터는 토큰의 유효 시간
    }

    // 리프레시 토큰 생성 메소드
    public String generateRefreshToken(User user) {
        return this.generateToken(user.getUserId(), jwtConfig.getRefreshTokenExpiry());
    }

    // 토큰 생성 메소드
    public String generateToken(Long userId, int expiry) {
        // ttl = time to limit -> 유효시간 또는 expiry로 사용
        Date now = new Date(); // 현재 시간

        // jjwt에서 바로 접근할 수 있도록 인스턴스화 되어 있음
        return Jwts.builder() // JWT 빌더 객체 생성
                .header().type(jwtConfig.getType()) // header.type 셋
                .and() // payload를 연결한다는 의미, 다른 파츠 추가 연결 메소드
                .setSubject(String.valueOf(userId)) // PK 또는 id를 넣는다. 값은 기본적으로 String인데, 가지고 있는 값이 다르다면 형변환을 해야 한다.
                // payload.sub 셋 (user pk)
                .setIssuer(jwtConfig.getIssuer()) // payload.iss 셋 (토큰 발급자)
                .setIssuedAt(now) // DATE 객체를 넣으면 됨, payload.iat (토큰 발급 시간)
                .setExpiration(new Date(now.getTime() + expiry)) // 마찬가지로 DATE 객체이지만 계산을 해야 한다? ttl로 작성 시 expiry가 아닌 ttl
                // 토큰 만료 시간
                // .signWith(secretKey, SignatureAlgorithm.HS256)
                .signWith(secretKey)
                .compact();
    }
}
