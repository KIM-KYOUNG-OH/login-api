package com.study.loginapi.global;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse<T> {
    private final int status;
    private final T data;
    private final String message;
    private final String code;

    protected SuccessResponse(HttpStatus status, T data) {
        this.status = status.value();
        this.data = data;
        this.message = "success";
        this.code = null;
    }

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(HttpStatus.OK, data);
    }
}
