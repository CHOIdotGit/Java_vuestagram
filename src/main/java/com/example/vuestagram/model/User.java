package com.example.vuestagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class) //vue의 watchers? 같은 것
@Table(name = "users")
@SQLDelete(sql = "UPDATE Users SET updated_at = now(), deleted_at = NOW() WHERE user_id = ?") // 기본 값은 물리 삭제. deletedAt을 감지하지 않는다.
@Where(clause = "deleted_at IS NULL")
public class User {
    @Id // PK로 설정되어 기본적으로 NOT NULL이 적용되어 있다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increments처럼 1씩 증가
    @Column(name = "user_id") // 약간 제한적, 간단한 제약 조건. 모든 것을 설정하지는 않고, NOT NULL, size(크기)
    private Long userId; // Long은 MariaDB에서 BIGINT

    @Column(name = "account", unique = true, nullable = false, length = 20)
    private String account; // 데이터 타입은 앞의 'String'으로 설정한다.

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 20) // name을 넣으면 에러가 나올 수 있다.
    private String name;

    @Column(name = "profile", length = 100)
    private String profile;

    @Column(name = "gender", nullable = false, length = 1) // DB에 제약조건을 적용하는 이유? 데이터의 무결성을 지키기 위한 규칙
    private String gender;

    @JsonIgnore
    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAT;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAT;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAT;
    // DTO 객체 - 자바에서는 모델로 부르지만 JPA에서는 엔티티(요소) 클래스
    // JPA로 만들 예정이라 나중에 바꿀듯
}
