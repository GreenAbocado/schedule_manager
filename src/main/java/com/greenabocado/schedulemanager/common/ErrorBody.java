package com.greenabocado.schedulemanager.common;

import lombok.Getter;

import java.time.LocalDateTime;

/* 에러 응답 바디 객체 */
@Getter
public class ErrorBody {
    private final LocalDateTime timestamp;
    private final String code;
    private final String message;

    private ErrorBody(String code, String message) {
        timestamp = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }

    public static ErrorBody by(ErrorCode error) {
        return new ErrorBody(error.getCode(), error.getMessage());
    }
}