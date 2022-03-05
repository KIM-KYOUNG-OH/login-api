package com.study.loginapi.controller;

import com.study.loginapi.entity.Member;
import com.study.loginapi.service.MemberService;
import com.study.loginapi.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/{id}")
    public Member findMemberById(@PathVariable("id") String memberId) {
        return memberService.findMemberById(memberId);
    }

}
