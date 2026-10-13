package com.greenabocado.schedulemanager.common;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import static com.greenabocado.schedulemanager.common.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            MissingServletRequestParameterException.class,  // 필수 쿼리 누락 (스프링)
            MethodArgumentTypeMismatchException.class,  // 쿼리, 경로변수 타입 변환 실패 (스프링)
            HttpMessageNotReadableException.class,  // Json 형식 오류 및 타입 변환 실패 (스프링)
            IllegalArgumentException.class   // 이후 검증 로직에서 따로 던질 예외
    })
    protected ApiResponse<Void> handleInputException(Exception e) {
        return ApiResponse.fail(ErrorBody.by(INVALID_INPUT));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ApiResponse<Void> handleNoResourceFoundException(NoResourceFoundException e) {
        return ApiResponse.fail(ErrorBody.by(URL_NOT_FOUND));
    }

    @ExceptionHandler(CustomException.class)
    protected ApiResponse<Void> handleCustomException(CustomException e) {
        return ApiResponse.fail(ErrorBody.by(e.getError()));
    }

    /* 이후 클라이언트 예외 발견 시 추가 필요 */

    @ExceptionHandler(Exception.class)
    protected ApiResponse<Void> handleException(Exception e) {
        return ApiResponse.fail(ErrorBody.by(SERVER_ERROR));
    }
}