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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        boolean isExist = memberService.findByEmail(signUpRequest.getEmail());
        if(isExist) {
            throw new DuplicatedEmailException("Current email is duplicated!");
        }

        isExist = memberService.findByNickname(signUpRequest.getNickname());
        if(isExist) {
            throw new DuplicatedNicknameException("Current nickname is duplicated!");
        }

        String password = passwordEncoder.encode(signUpRequest.getPassword());
        Member member = Member.builder(signUpRequest.getNickname(), password)
                        .email(signUpRequest.getEmail())
                        .build();

        Long memberId = memberService.saveMember(member);
        if(memberId == null) {
            throw new CannotGenerateIdException("Fail to generate id!");
        }

        return memberId;
    }

    public TokenResponse login(SignInRequest signInRequest) {

        Member member = memberService.findMemberByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new AbsentMemberException("Current user is not existed!"));

        if(!passwordEncoder.matches(signInRequest.getPassword(), member.getPassword())) {
            throw new WrongPasswordException("Current password is wrong");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        Auth findAuth = authService.findAuthByMemberId(member.getMemberId());  // Optional로 우아하게 바꿀 수 있을까?
        if(findAuth == null) {
            authService.saveAuth(accessToken, refreshToken, member.getMemberId());
        } else {
            authService.updateAuth(findAuth.getAuthId(), accessToken, refreshToken, member.getMemberId());
        }

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
