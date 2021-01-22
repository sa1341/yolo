package com.junyoung.yolo.domain.member.service;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import com.junyoung.yolo.exception.MemberNotFoundException;

import java.util.Optional;

public final class MemberServiceHelper {
    public static Member findExistingMember(MemberRepository repo, String memberId) {
        Optional<Member> findMember = repo.findById(memberId);

        if (!findMember.isPresent()) {
            throw new MemberNotFoundException(memberId);
        }

        return findMember.get();
    }
}
