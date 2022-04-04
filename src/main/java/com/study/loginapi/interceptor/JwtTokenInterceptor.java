package com.study.loginapi.interceptor;

import com.study.loginapi.exception.ExpiredTokenException;
import com.study.loginapi.exception.IncorrectRefreshTokenException;
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

        boolean isValidAccessToken = jwtTokenProvider.isValidToken(accessToken);
        boolean isValidRefreshToken = jwtTokenProvider.isValidToken(refreshToken);

        if(isValidAccessToken) {
            return true;
        } else {
            if(isValidRefreshToken) {
                Long memberId = jwtTokenProvider.getMemberId(refreshToken);
                String refreshTokenInDB = authService.findAuthByMemberId(memberId).getRefreshToken();
                if(!refreshToken.equals(refreshTokenInDB)) {
                    throw new IncorrectRefreshTokenException("RefreshToken is not equal with DB!");
                }

                response.setStatus(401);
                response.setHeader("ACCESS_TOKEN", accessToken);
                response.setHeader("REFRESH_TOKEN", refreshToken);
                response.setHeader("msg", "Recreate Access Token and Refresh Token!");
                return false;
            }
            throw new ExpiredTokenException("Please login again!");
        }
    }
}
