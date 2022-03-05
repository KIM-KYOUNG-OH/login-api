package com.study.loginapi.service;

import com.study.loginapi.dto.SignInRequestDto;
import com.study.loginapi.dto.SignUpRequestDto;
import com.study.loginapi.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberService memberService;

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        String memberId = signUpRequestDto.getMemberId();
        int cnt = memberService.getCountById(memberId);
        if(cnt >= 1) {
            throw new DuplicateKeyException("id is duplicated");
        }

        Member member = Member.builder(memberId)
                        .name(signUpRequestDto.getName())
                        .password(signUpRequestDto.getPassword())
                        .email(signUpRequestDto.getEmail())
                        .build();

        memberService.saveMember(member);
    }

    public String login(SignInRequestDto signInRequestDto) {

        String memberId = signInRequestDto.getId();
        int cnt = memberService.getCountById(memberId);
        if(cnt == 0) {
            throw new IllegalArgumentException("current user is not existed");
        }

        return signInRequestDto.getId();
    }
}
