package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.entity.QMember;
import com.junyoung.yolo.exception.MemberNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @Before
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Member member1 = Member.create("junyoung", 30);
        Member member2 = Member.create("minwan", 30);
        Member member3 = Member.create("seungtop", 30);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void saveMember() {
        QMember member = QMember.member;

        Member findMember = queryFactory
                    .select(member)
                    .from(member)
                    .where(member.name.eq("11"))
                    .fetchOne();

        exceptionRule.expect(MemberNotFoundException.class);
        exceptionRule.expectMessage("Member is not exist");
        if (findMember == null) throw new MemberNotFoundException("Member is not exist");

        //then
        assertThat("junyoung").isEqualTo(findMember.getName());
     }
}
