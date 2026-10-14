package com.greenabocado.schedulemanager.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import static com.greenabocado.schedulemanager.common.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /* 요청 형식 예외 (스프링 기반) */
    @ExceptionHandler({
            // MissingServletRequestParameterException.class,  필수 쿼리 누락, 일정 조회 쿼리가 필수가 아니라 필요 없음
            MethodArgumentTypeMismatchException.class,  // 쿼리, 경로변수 타입 변환 실패
            HttpMessageNotReadableException.class,  // Json 형식 오류 및 타입 변환 실패
    })
    protected ResponseEntity<ApiResponse<Void>> handleInputException(Exception e, HttpServletRequest request) {

        /* 역직렬화 중, 생성자 내 검증 예외가 HttpMessageNotReadableException으로 래핑되는 문제 해결 */
        if (e instanceof HttpMessageNotReadableException) {
            Throwable cause = e.getCause();
            while (cause != null) {
                if (cause instanceof IllegalArgumentException) {
                    return handleIllegalArgumentException((IllegalArgumentException)cause, request);
                }
                cause = cause.getCause();
            }
        }

        log.warn("[400 Bad Request] | {} {} | by: {}",
                request.getMethod(), request.getRequestURI(), e.getClass().getSimpleName());

        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(INVALID_INPUT)));
    }

    /* 유효성 검증 예외 (직접 던진 예외) */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.warn("[400 Bad Request] | {} {} | message: {}",
                request.getMethod(), request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(INVALID_INPUT, e.getMessage())));
    }

    /* 비즈니스 예외 */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e, HttpServletRequest request) {
        ErrorCode error = e.getError();
        log.warn("[{} {}] | {} {} | message: {}", error.getStatus().value(), error.getStatus().getReasonPhrase(),
                request.getMethod(), request.getRequestURI(), error.getCode());

        return ResponseEntity.status(error.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(error)));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(HttpServletRequest request) {
        log.warn("[404 Not Found] | {} {} | Url found fail",
                request.getMethod(), request.getRequestURI());

        return ResponseEntity.status(URL_NOT_FOUND.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(URL_NOT_FOUND)));
    }

    /* 이후 클라이언트 예외 발견 시 추가 필요 */

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleException(Exception e, HttpServletRequest request) {
        log.error("[500 Internal Server Error] | {} {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(SERVER_ERROR.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(SERVER_ERROR)));
    }
}