package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.entity.MemberRole;
import com.junyoung.yolo.domain.member.entity.QMember;
import com.junyoung.yolo.domain.member.repository.MemberRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.junyoung.yolo.domain.member.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Before
    public void before() {
        Member member1 = Member.builder()
                .id("a790077714@gmail.com")
                .name("junyoung")
                .age(30)
                .build();
        member1.addMemberRole(MemberRole.USER);
        memberRepository.save(member1);
    }

    @Test
    public void findMemberById() throws Exception {
        //given
        //when
        Member member = Optional.ofNullable(queryFactory.selectFrom(QMember.member)
                .where(QMember.member.id.eq("a79007714@gmail.com"))
                .fetchOne()).orElseThrow(() ->  new Exception());

        //then
        Assertions.assertThat(member.getId()).isEqualTo("a79007714@gmail.com");
     }

     /**
        페이징 처리를 위한 테스트 코드 작
      **/
     @Test
     public void testPageDefault() throws Exception {
         //given
         QueryResults<Member> results = queryFactory.selectFrom(member)
                 .fetchResults();

         //when
         List<Member> members = results.getResults();
         long totalCount = results.getTotal();

         //then
         assertThat(members.size()).isEqualTo(totalCount);
      }

      @Test
      public void paging1() throws Exception {
          //given
          List<Member> result = queryFactory
                  .selectFrom(member)
                  .orderBy(member.name.desc())
                  .offset(1)
                  .limit(2)
                  .fetch();

          //when
          result.forEach(m -> System.out.println(m.getName()));

          //then
          assertThat(result.size()).isEqualTo(1);
      }
}
