package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveMember() throws Exception {

        //given
        Member member = Member.builder()
                            .name("junyoung")
                            .age(31)
                            .build();
        //when
        memberRepository.save(member);

        System.out.println("memberId: " + member.getId());

        Member findMember = memberRepository.findById(member.getId()).get();

        //then
        assertThat("junyoung").isEqualTo(findMember.getName());
     }
}
