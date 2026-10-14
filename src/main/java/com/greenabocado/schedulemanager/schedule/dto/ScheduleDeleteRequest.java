package com.greenabocado.schedulemanager.schedule.dto;

import static com.greenabocado.schedulemanager.schedule.ScheduleValidExceptionMessage.INVALID_PASSWORD;

public record ScheduleDeleteRequest(String password) {

    /* 기본 검증: 비밀번호 누락으로 인한 기본값(null), 공백 */
    public ScheduleDeleteRequest {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
    }
}