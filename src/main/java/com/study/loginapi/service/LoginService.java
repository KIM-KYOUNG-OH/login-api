package com.study.loginapi.service;

import com.study.loginapi.dto.SignUpRequestDto;

public interface LoginService {

    void signUp(SignUpRequestDto signUpRequestDto);
}
