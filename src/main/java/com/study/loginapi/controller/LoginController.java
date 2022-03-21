package com.study.loginapi.controller;

import com.study.loginapi.dto.SignInRequestDto;
import com.study.loginapi.dto.SignUpRequestDto;
import com.study.loginapi.service.LoginService;
import com.sun.jdi.request.DuplicateRequestException;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/member")
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        loginService.signUp(signUpRequestDto);
    }

    @PostMapping("/login")
    public HttpStatus login(@RequestBody SignInRequestDto signInRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();

        log.info("세션 아이디: " + session.getId());
        log.info("세션 생성 시간: " + session.getCreationTime());
        log.info("최근 세션 접근 시간: " + session.getLastAccessedTime());
        session.setMaxInactiveInterval(600);
        log.info("세션 유효 시간: " + session.getMaxInactiveInterval() + " sec");

        if(session.getAttribute("loginId") != null) throw new DuplicateRequestException("already login");

        String loginId = loginService.login(signInRequestDto);

        session.setAttribute("loginId", loginId);

        return HttpStatus.OK;
    }

    @PostMapping("/logout")
    public HttpStatus logout(HttpSession session) {
        if(session.getAttribute("loginId") == null) throw new InvalidRequestStateException("already logout");

        session.invalidate();

        return HttpStatus.OK;
    }

    // 특정 유저의 로그인 상태 정보 조회 api
    // session storage나 token 방식 사용
}
