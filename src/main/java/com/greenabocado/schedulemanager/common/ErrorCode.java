package com.greenabocado.schedulemanager.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /* 공통 */
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "잘못된 요청입니다."),
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "URL_NOT_FOUND", "잘못된 요청 경로입니다."),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_ERROR", "서버 내부 오류입니다"),

    /* 일정 관련 */
    SCHEDULE_NOT_EXIST(HttpStatus.NOT_FOUND, "SCHEDULE_NOT_EXIST", "해당 일정이 존재하지 않습니다."),
    SCHEDULE_AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "SCHEDULE_AUTHENTICATION_FAIL", "일정 변경 비밀번호 인증에 실패하였습니다."),

    /* 댓글 관련 */
    COMMENT_OVER_LIMIT(HttpStatus.BAD_REQUEST, "COMMENT_OVER_LIMIT", "댓글 개수 제한을 초과하였습니다.");

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private final HttpStatus status;
    private final String code;
    private final String message;
}
