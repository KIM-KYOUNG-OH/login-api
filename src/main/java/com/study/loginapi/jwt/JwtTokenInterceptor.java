package com.study.loginapi.jwt;

import com.study.loginapi.entity.Auth;
import com.study.loginapi.entity.Member;
import com.study.loginapi.exception.AbsentMemberException;
import com.study.loginapi.exception.ExpiredTokenException;
import com.study.loginapi.exception.IncorrectRefreshTokenException;
import com.study.loginapi.service.AuthService;
import com.study.loginapi.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final MemberService memberService;

    public JwtTokenInterceptor(JwtTokenProvider jwtTokenProvider, AuthService authService, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
        this.memberService = memberService;
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
                String email = jwtTokenProvider.getEmail(refreshToken);
                Member findMember = memberService.findMemberByEmail(email)
                        .orElseThrow(() -> new AbsentMemberException("Current user is not existed!"));
                String refreshTokenInDB = authService.findAuthByMemberId(findMember.getMemberId()).getRefreshToken();
                if(!refreshToken.equals(refreshTokenInDB)) {
                    throw new IncorrectRefreshTokenException("RefreshToken is not equal with DB!");
                }

                String newAccessToken = jwtTokenProvider.createAccessToken(email);
                String newRefreshToken = jwtTokenProvider.createRefreshToken(email);

                Auth findAuth = authService.findAuthByMemberId(findMember.getMemberId());
                if(findAuth == null) {
                    authService.saveAuth(newRefreshToken, findMember.getMemberId());
                } else {
                    authService.updateAuth(findAuth.getAuthId(), newRefreshToken, findMember.getMemberId());
                }

                response.setStatus(401);
                response.setHeader("ACCESS_TOKEN", newAccessToken);
                response.setHeader("REFRESH_TOKEN", newRefreshToken);
                response.setHeader("msg", "Recreate Access Token and Refresh Token!");
                return false;
            }
            throw new ExpiredTokenException("Please login again!");
        }
    }
}
