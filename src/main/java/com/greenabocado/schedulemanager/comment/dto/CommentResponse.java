package com.greenabocado.schedulemanager.comment.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import com.greenabocado.schedulemanager.comment.Comment;


@Builder
public record CommentResponse(
        Long id, String content, String creator, Long scheduleId, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .creator(comment.getCreator())
                .scheduleId(comment.getScheduleId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt()).build();
    }
}