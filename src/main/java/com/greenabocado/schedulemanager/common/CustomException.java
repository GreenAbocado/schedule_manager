package com.greenabocado.schedulemanager.common;

import lombok.Getter;

/* 커스텀 예외 + 에러 코드를 통한 비즈니스 예외 구체화 */
public class CustomException extends RuntimeException {
    public CustomException(ErrorCode error) {
        this.error = error;
    }

    @Getter
    private final ErrorCode error;
}