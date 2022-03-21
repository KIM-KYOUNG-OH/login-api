package com.study.loginapi.service;

import com.study.loginapi.entity.Member;
import com.study.loginapi.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    public Member findMemberById(String memberId) {
        return memberMapper.findOneById(memberId);
    }

    public int getCountById(String memberId) {
        return memberMapper.getCountById(memberId);
    }

    @Transactional
    public void saveMember(Member member) {
        memberMapper.saveOne(member);
    }
}
