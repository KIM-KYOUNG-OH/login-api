package com.study.loginapi.global;

import com.study.loginapi.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int status;
    private final String data;
    private final String message;
    private final String code;

    protected ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.data = null;
        this.message = errorCode.getMessage();
        this.code = errorCode.getErrorCode();
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}
