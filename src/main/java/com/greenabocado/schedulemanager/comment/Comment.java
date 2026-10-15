package com.greenabocado.schedulemanager.comment;

import lombok.*;
import jakarta.persistence.*;
import java.util.regex.Pattern;
import com.greenabocado.schedulemanager.common.BaseEntity;
import static com.greenabocado.schedulemanager.comment.CommentValidExceptionMessage.*;

@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    /* 도메인 필드 검증 조건 */
    /* 작성자, 비밀번호 최대 길이 공통 상수 (변경 시 상수 분리) */
    private static final int INFO_MAX_LENGTH = 20;
    private static final int CONTENT_MAX_LENGTH = 100;
    private static final int CREATOR_MIN_LENGTH = 1;
    private static final int PASSWORD_MIN_LENGTH = 4;
    /* 단순한 패턴 사용 (필요시 변경) */
    private static final Pattern CREATOR_PATTERN = Pattern.compile(
            String.format("[a-zA-Z가-힣]{%d,%d}", CREATOR_MIN_LENGTH, INFO_MAX_LENGTH));
    /* 비번 원문 그대로 저장 */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            String.format("[a-zA-Z0-9]{%d,%d}", PASSWORD_MIN_LENGTH, INFO_MAX_LENGTH));

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 요구사항에 수정 기능 추가시 updatable=false 제거 */

    @Column(length=CONTENT_MAX_LENGTH, nullable=false, updatable=false)
    private String content;

    @Column(length=INFO_MAX_LENGTH, nullable=false, updatable=false)
    private String creator;

    @Column(length=INFO_MAX_LENGTH, nullable=false, updatable=false)
    private String password;

    @Column(nullable=false, updatable=false)
    private long scheduleId;

    @Builder
    private Comment(String content, String creator, String password, Long scheduleId) {
        validateComment(content);
        validateCreator(creator);
        validatePassword(password);
        validateScheduleId(scheduleId);
        this.content = content;
        this.creator = creator;
        this.password = password;
        this.scheduleId = scheduleId;
    }

    private static void validateComment(String comment) {
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException(INVALID_CONTENT);
        }
        if (comment.length() > CONTENT_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format(INVALID_CONTENT_FORMAT, CONTENT_MAX_LENGTH));
        }
    }

    private static void validateCreator(String creator) {
        if (creator == null || creator.isBlank()) {
            throw new IllegalArgumentException(INVALID_CREATOR);
        }
        if (!CREATOR_PATTERN.matcher(creator).matches()) {
            throw new IllegalArgumentException(String.format(INVALID_CREATOR_FORMAT, CREATOR_MIN_LENGTH, INFO_MAX_LENGTH));
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(String.format(INVALID_PASSWORD_FORMAT, PASSWORD_MIN_LENGTH, INFO_MAX_LENGTH));
        }
    }

    private static void validateScheduleId(Long scheduleId) {
        if (scheduleId == null) {
            throw new IllegalArgumentException(INVALID_SCHEDULE_ID);
        }
        if (scheduleId < 1) {
            throw new IllegalArgumentException(INVALID_SCHEDULE_ID_FORMAT);
        }
    }
}