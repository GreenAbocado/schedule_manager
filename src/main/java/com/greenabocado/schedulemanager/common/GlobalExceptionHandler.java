package com.greenabocado.schedulemanager.common;

import org.springframework.http.ResponseEntity;
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
    protected ResponseEntity<ApiResponse<Void>> handleInputException() {
        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(INVALID_INPUT)));
    }

    /* 직접 던진 검증 예외 */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(INVALID_INPUT, e.getMessage())));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException() {
        return ResponseEntity.status(URL_NOT_FOUND.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(URL_NOT_FOUND)));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode error = e.getError();
        return ResponseEntity.status(error.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(error)));
    }

    /* 이후 클라이언트 예외 발견 시 추가 필요 */

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Void>> handleException() {
        return ResponseEntity.status(SERVER_ERROR.getStatus())
                .body(ApiResponse.fail(ErrorBody.from(SERVER_ERROR)));
    }
}