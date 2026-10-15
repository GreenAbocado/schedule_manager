package com.greenabocado.schedulemanager.schedule;

import lombok.*;
import jakarta.persistence.*;
import java.util.regex.Pattern;
import com.greenabocado.schedulemanager.common.BaseEntity;
import static com.greenabocado.schedulemanager.schedule.ScheduleValidExceptionMessage.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule extends BaseEntity {

    /* 도메인 필드 검증 조건 */
    /* creator, password 최대 길이. 특정 길이 변경 시 분리 */
    private static final int INFO_MAX_LENGTH = 20;
    private static final int TITLE_MAX_LENGTH = 30;
    private static final int CONTENT_MAX_LENGTH = 200;
    private static final int CREATOR_MIN_LENGTH = 1;
    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final Pattern CREATOR_PATTERN = Pattern.compile(
            String.format("[a-zA-Z가-힣]{%d,%d}", CREATOR_MIN_LENGTH, INFO_MAX_LENGTH));
    /* 비번 원문 그대로 저장 */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            String.format("[a-zA-Z0-9]{%d,%d}", PASSWORD_MIN_LENGTH, INFO_MAX_LENGTH));


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=TITLE_MAX_LENGTH, nullable=false)
    private String title;

    @Column(length=CONTENT_MAX_LENGTH, nullable=false, updatable=false)
    private String content;

    @Column(length=INFO_MAX_LENGTH, nullable=false)
    private String creator;

    @Column(length=INFO_MAX_LENGTH, nullable=false, updatable=false)
    private String password;


    @Builder
    private Schedule(String title, String content, String creator, String password) {
        validateTitle(title);
        validateContent(content);
        validateCreator(creator);
        validatePassword(password);

        this.title = title;
        this.content = content;
        this.creator = creator;
        this.password = password;
    }

    public void updateInfo(String title, String creator) {
        validateTitle(title);
        validateCreator(creator);
        this.title = title;
        this.creator = creator;
    }


    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(INVALID_TITLE);
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format(INVALID_TITLE_FORMAT, TITLE_MAX_LENGTH));
        }
    }

    private static void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException(INVALID_CONTENT);
        }

        if (content.length() > CONTENT_MAX_LENGTH) {
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
}