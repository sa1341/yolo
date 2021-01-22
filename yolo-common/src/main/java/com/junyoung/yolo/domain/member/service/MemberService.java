package com.junyoung.yolo.domain.member.service;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.junyoung.yolo.domain.member.service.MemberServiceHelper.findExistingMember;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public Member findById(String memberId) {
        Member findMember = findExistingMember(memberRepository, memberId);
        return findMember;
    }
}

