package com.study.loginapi.interceptor;

import com.study.loginapi.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader("ACCESS_TOKEN");
        log.info("AccessToken: " + accessToken);
        String RefreshToken = request.getHeader("REFRESH_TOKEN");
        log.info("RefreshToken: " + RefreshToken);

        if(accessToken != null && jwtTokenProvider.isValidToken(accessToken)) {
            return true;
        }

        return false;
    }
}
