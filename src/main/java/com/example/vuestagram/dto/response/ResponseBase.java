package com.example.vuestagram.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // Setter를 사용해도 되나, 상황에 따라 변경할 수 있다.
public class ResponseBase<T> {
    private String message;
    private int status; // 코드보다 status를 넣을 예정
    private T data;

}
