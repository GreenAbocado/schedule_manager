package com.greenabocado.schedulemanager.schedule.dto;

import com.greenabocado.schedulemanager.comment.Comment;
import com.greenabocado.schedulemanager.comment.dto.CommentResponse;
import com.greenabocado.schedulemanager.schedule.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;


/* comment 패키지 의존 */
@Builder
public record ScheduleDetailResponse(Long id, String title, String content, String creator,
                                     List<CommentResponse> comments,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static ScheduleDetailResponse from(Schedule schedule, List<Comment> comments) {
        return ScheduleDetailResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .creator(schedule.getCreator())
                .comments(comments.stream().map(CommentResponse::from).toList())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt()).build();
    }
}
