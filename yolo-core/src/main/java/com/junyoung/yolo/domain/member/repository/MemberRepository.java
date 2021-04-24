package com.junyoung.yolo.domain.member.repository;

import com.junyoung.yolo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
