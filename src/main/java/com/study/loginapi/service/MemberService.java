package com.study.loginapi.service;

import com.study.loginapi.entity.Member;
import com.study.loginapi.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;

    public Optional<Member> findMemberById(String memberId) {
        return memberMapper.findOneById(memberId);
    }

    public Optional<Member> findMemberByEmail(String email) {
        return memberMapper.findOneByEmail(email);
    }

    public boolean findByNickname(String nickname) {
        return memberMapper.findByNickname(nickname);
    }

    public int getCountById(String memberId) {
        return memberMapper.getCountById(memberId);
    }

    @Transactional
    public Optional<Long> saveMember(Member member) {
        memberMapper.saveOne(member);
        log.info("new memberId: " + member.getMemberId());
        return Optional.ofNullable(member.getMemberId());
    }

    public boolean findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }
}
