package com.greenabocado.schedulemanager.comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class CommentValidExceptionMessage {

    /* 유효성 검증은 IllegalArgumentException 하나로 처리하므로 메시지로 구체화 */

    /* 필드 기본 유효성 예외 (누락(null) 및 공백) */
    public static final String INVALID_CONTENT = "필수 입력 값인 content를 입력해야 합니다.";
    public static final String INVALID_CREATOR = "필수 입력 값인 creator를 입력해야 합니다.";
    public static final String INVALID_PASSWORD = "필수 입력 값인 password를 입력해야 합니다.";
    public static final String INVALID_SCHEDULE_ID = "필수 입력 값인 schedule_id를 입력해야 합니다.";

    /* 필드 비즈니스 규칙 검증 예외 */
    public static final String INVALID_CONTENT_FORMAT = "content는 %d자 이하로 입력해야 합니다.";
    public static final String INVALID_CREATOR_FORMAT = "creator는 영문 대소문자, 한글 혼용 %d~%d자로 입력해야 합니다.";
    public static final String INVALID_PASSWORD_FORMAT = "password는 영문 대소문자, 숫자 혼용 %d~%d자로 입력해야 합니다.";
    public static final String INVALID_SCHEDULE_ID_FORMAT = "schedule_id는 1이상의 값이어야 합니다.";

}