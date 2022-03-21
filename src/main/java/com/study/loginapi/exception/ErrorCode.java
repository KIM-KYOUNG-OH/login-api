package com.study.loginapi.exception;

public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(500, "C004", "Internal server Error"),

    // Member
    DUPLICATED_NICKNAME_ERROR(400, "M001", "NickName is duplicated"),
    DUPLICATED_EMAIL_ERROR(400, "M002", "Email is duplicated"),
    ABSENT_MEMBER_ERROR(400, "M003", "Current user is not existed"),

    // Auth
    ABSENT_TOKEN_ERROR(400, "A001", "Current user has not any token!"),
    WRONG_PASSWORD_ERROR(400, "A002", "Current password is wrong");

    private final int status;
    private final String errorCode;
    private final String message;

    ErrorCode(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
