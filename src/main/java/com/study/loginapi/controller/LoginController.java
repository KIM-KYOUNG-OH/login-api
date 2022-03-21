package com.study.loginapi.controller;

import com.study.loginapi.dto.SignInRequest;
import com.study.loginapi.dto.SignUpRequest;
import com.study.loginapi.dto.TokenResponse;
import com.study.loginapi.global.SuccessResponse;
import com.study.loginapi.service.LoginService;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/signup")
    public SuccessResponse<Long> signUp(@RequestBody SignUpRequest signUpRequest) {
        Long memberId = loginService.signUp(signUpRequest);
        return SuccessResponse.of(memberId);
    }

    @PostMapping("/login")
    public SuccessResponse<TokenResponse> login(@RequestBody SignInRequest signInRequest) {
        return SuccessResponse.of(loginService.login(signInRequest));
    }

//    @PostMapping("/logout")
//    public HttpStatus logout(HttpSession session) {
//        if (session.getAttribute("loginId") == null) throw new InvalidRequestStateException("already logout");
//
//        session.invalidate();
//
//        return HttpStatus.OK;
//    }

    // 특정 유저의 로그인 상태 정보 조회 api
    // session storage나 token 방식 사용
}
