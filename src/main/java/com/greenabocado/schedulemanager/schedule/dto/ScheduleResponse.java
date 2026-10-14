package com.greenabocado.schedulemanager.schedule.dto;
import com.greenabocado.schedulemanager.schedule.Schedule;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ScheduleResponse(Long id, String title, String content, String creator,
                               LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .content(schedule.getContent())
                    .creator(schedule.getCreator())
                    .createdAt(schedule.getCreatedAt())
                    .updatedAt(schedule.getUpdatedAt()).build();
    }
}