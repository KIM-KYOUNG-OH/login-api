package com.study.loginapi.interceptor;

import com.study.loginapi.jwt.JwtTokenProvider;
import com.study.loginapi.service.AuthService;
import com.study.loginapi.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginService loginService;
    private final AuthService authService;

    public JwtTokenInterceptor(JwtTokenProvider jwtTokenProvider, LoginService loginService, AuthService authService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginService = loginService;
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader("ACCESS_TOKEN");
        String refreshToken = request.getHeader("REFRESH_TOKEN");

        if(accessToken != null && jwtTokenProvider.isValidToken(accessToken)) {
            return true;
        }

        return false;
    }
}
