package com.sw.fd.service;

import com.sw.fd.entity.Member;
import com.sw.fd.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(Member member) {
        member.setMdate(LocalDate.now()); // 가입 날짜를 현재 날짜로 설정
        return memberRepository.save(member);
    }
}
