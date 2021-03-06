package com.study.loginapi.global;

import com.study.loginapi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedNicknameException.class)
    protected ErrorResponse handleDuplicatedNicknameException(DuplicatedNicknameException e) {
        return ErrorResponse.of(ErrorCode.DUPLICATED_NICKNAME_ERROR);
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    protected ErrorResponse handleDuplicatedEmailException(DuplicatedEmailException e) {
        return ErrorResponse.of(ErrorCode.DUPLICATED_EMAIL_ERROR);
    }

    @ExceptionHandler(AbsentMemberException.class)
    protected ErrorResponse handleAbsentMemberException(AbsentMemberException e) {
        return ErrorResponse.of(ErrorCode.ABSENT_MEMBER_ERROR);
    }

    @ExceptionHandler(AbsentTokenException.class)
    protected ErrorResponse handleAbsentTokenException(AbsentTokenException e) {
        return ErrorResponse.of(ErrorCode.ABSENT_TOKEN_ERROR);
    }

    @ExceptionHandler(WrongPasswordException.class)
    protected ErrorResponse handleWrongPasswordException(WrongPasswordException e) {
        return ErrorResponse.of(ErrorCode.WRONG_PASSWORD_ERROR);
    }

    @ExceptionHandler(CannotGenerateIdException.class)
    protected ErrorResponse handleCannotGenerateIdException(CannotGenerateIdException e) {
        return ErrorResponse.of(ErrorCode.CANNOT_GENERATE_ID_ERROR);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    protected ErrorResponse handleExpiredTokenException(ExpiredTokenException e) {
        return ErrorResponse.of(ErrorCode.EXPIRED_TOKEN_ERROR);
    }

    @ExceptionHandler(IncorrectRefreshTokenException.class)
    protected ErrorResponse handleIncorrectRefreshTokenException(IncorrectRefreshTokenException e) {
        return ErrorResponse.of(ErrorCode.INCORRECT_REFRESH_TOKEN_ERROR);
    }

    // ???????????? ?????? ??????????????? ?????? ????????? ????????? ????????? ?????? ???????????? ?????????.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
