package com.greenabocado.schedulemanager.common;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import static com.greenabocado.schedulemanager.common.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ /* 스프링 자체 예외 */
            MissingServletRequestParameterException.class,  // 필수 쿼리 누락
            MethodArgumentTypeMismatchException.class,  // 쿼리, 경로변수 타입 변환 실패
            HttpMessageNotReadableException.class,  // Json 형식 오류 및 타입 변환 실패
    })
    protected ApiResponse<Void> handleInputException() {
        return ApiResponse.fail(ErrorBody.from(INVALID_INPUT));
    }

    /* 직접 던진 검증 예외 */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.fail(ErrorBody.from(INVALID_INPUT, e.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ApiResponse<Void> handleNoResourceFoundException(NoResourceFoundException e) {
        return ApiResponse.fail(ErrorBody.from(URL_NOT_FOUND));
    }

    @ExceptionHandler(CustomException.class)
    protected ApiResponse<Void> handleCustomException(CustomException e) {
        return ApiResponse.fail(ErrorBody.from(e.getError()));
    }

    /* 이후 클라이언트 예외 발견 시 추가 필요 */

    @ExceptionHandler(Exception.class)
    protected ApiResponse<Void> handleException() {
        return ApiResponse.fail(ErrorBody.from(SERVER_ERROR));
    }
}