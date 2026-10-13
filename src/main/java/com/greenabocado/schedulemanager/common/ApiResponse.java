package com.greenabocado.schedulemanager.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;

/* 공통 응답 표준화 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final ErrorBody error;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<Void> fail (ErrorBody error) {
        return new ApiResponse<>(false, null, error);
    }
}