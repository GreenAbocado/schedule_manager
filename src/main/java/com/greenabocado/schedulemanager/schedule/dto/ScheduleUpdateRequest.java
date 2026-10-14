package com.greenabocado.schedulemanager.schedule.dto;

import static com.greenabocado.schedulemanager.schedule.ScheduleValidExceptionMessage.*;

public record ScheduleUpdateRequest(String title, String creator, String password) {

    /* 기본 검증: 필수값 누락으로 인한 기본값(null) 및 공백 */
    public ScheduleUpdateRequest {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(INVALID_TITLE);
        }
        if (creator == null || creator.isBlank()) {
            throw new IllegalArgumentException(INVALID_CREATOR);
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
        title = title.trim();
        creator = creator.trim();
    }
}