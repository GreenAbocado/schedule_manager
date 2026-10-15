package com.greenabocado.schedulemanager.comment.dto;

import com.greenabocado.schedulemanager.comment.Comment;
import static com.greenabocado.schedulemanager.comment.CommentValidExceptionMessage.*;

public record CommentCreateRequest(String content, String creator, String password) {

    /* 기본 검증: 필수값 누락에 따른 null 바인딩, 공백 */
    public CommentCreateRequest {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException(INVALID_CONTENT);
        }
        if (creator == null || creator.isBlank()) {
            throw new IllegalArgumentException(INVALID_CREATOR);
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }

        /* comment는 앞뒤 공백 가능, password는 자르면 사용자가 혼란 */
        creator = creator.trim();
    }

    public Comment toEntity(Long scheduleId) {
        return Comment.builder()
                .content(content())
                .creator(creator())
                .password(password())
                .scheduleId(scheduleId).build();
    }
}