package com.study.loginapi.service;

import com.study.loginapi.dto.SignInRequest;
import com.study.loginapi.dto.SignUpRequest;
import com.study.loginapi.dto.TokenResponse;
import com.study.loginapi.entity.Auth;
import com.study.loginapi.entity.Member;
import com.study.loginapi.exception.*;
import com.study.loginapi.jwt.JwtTokenProvider;
import com.study.loginapi.repository.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthMapper authMapper;

    @Transactional
    public Long signUp(SignUpRequest signUpRequest) {

        boolean isExisted = memberService.findByEmail(signUpRequest.getEmail());
        if(!isExisted) {
            throw new DuplicatedEmailException("Current email is duplicated!");
        }

        isExisted = memberService.findByNickname(signUpRequest.getNickname());
        if(!isExisted) {
            throw new DuplicatedNicknameException("Current nickname is duplicated!");
        }

        String password = passwordEncoder.encode(signUpRequest.getPassword());
        Member member = Member.builder(signUpRequest.getNickname(), password)
                        .email(signUpRequest.getEmail())
                        .build();
        memberService.saveMember(member);

        return member.getMemberId();
    }

    public TokenResponse login(SignInRequest signInRequest) {

        Member member = memberService.findMemberByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new AbsentMemberException("Current user is not existed!"));
        Auth auth = authService.findAuthByMemberId(member.getMemberId())
                .orElseThrow(() -> new AbsentTokenException("Current user has not any token!"));
        if(!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
            throw new WrongPasswordException("Current password is wrong");
        }

        String accessToken = "";
        String refreshToken = auth.getRefreshToken();

        if (jwtTokenProvider.isValidRefreshToken(refreshToken)) {
            accessToken = jwtTokenProvider.createAccessToken(member.getEmail()); //Access Token 새로 만들어서 줌
            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            //둘 다 새로 발급
            accessToken = jwtTokenProvider.createAccessToken(member.getEmail());
            refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());
            authService.updateRefreshToken(auth.getAuthId(), refreshToken);   //DB Refresh 토큰 갱신
        }

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
