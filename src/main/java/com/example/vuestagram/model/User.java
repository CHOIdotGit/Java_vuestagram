package com.example.vuestagram.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long userId;
    private String account;
    private String password;
    private String name;
    private String profile;
    private String gender;
    private String refreshToken;
    private String createdAT;
    private String updatedAT;
    private String deletedAT;
    // DTO 객체 - 자바에서는 모델로 부르지만 JPA에서는 엔티티(요소) 클래스
    // JPA로 만들 예정이라 나중에 바꿀듯
}
