package com.study.loginapi.service;

import com.study.loginapi.entity.Member;

public interface MemberService {

    Member findMemberById(String memberId);
    int getCountById(String memberId);

    void saveMember(Member member);
}
