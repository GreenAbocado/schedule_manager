package com.greenabocado.schedulemanager.schedule.dto;

import com.greenabocado.schedulemanager.schedule.Schedule;
import static com.greenabocado.schedulemanager.schedule.ScheduleValidExceptionMessage.*;

public record ScheduleCreateRequest(String title, String content, String creator, String password) {

    /* 기본 검증: 누락으로 인한 기본값(null) 및 공백 */
    public ScheduleCreateRequest {
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

    public Schedule toEntity() {
        return Schedule.builder()
                .title(title())
                .content(content())    // 반드시 매핑 필요 (선택 필드라 빌더 누락 시 null로 초기화)
                .creator(creator())
                .password(password()).build();
    }
}