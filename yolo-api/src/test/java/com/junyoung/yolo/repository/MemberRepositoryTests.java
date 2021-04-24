package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.entity.MemberRole;
import com.junyoung.yolo.domain.member.entity.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.junyoung.yolo.domain.member.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Member member1 = Member.builder()
                .id("a790077714@gmail.com")
                .name("junyoung")
                .age(30)
                .build();
        member1.addMemberRole(MemberRole.USER);
        em.persist(member1);
    }

    @DisplayName(value = "회원 아이디 조회 테스트")
    @Test
    public void findMemberById() throws Exception {

        //given
        Member member = Optional.ofNullable(queryFactory.selectFrom(QMember.member)
                .where(QMember.member.id.eq("a79007714@gmail.com"))
                .fetchOne()).orElseThrow(() ->  new Exception());

        //when
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
          //when
          List<Member> result = queryFactory
                  .selectFrom(member)
                  .orderBy(member.name.desc())
                  .offset(1)
                  .limit(2)
                  .fetch();

          result.forEach(m -> System.out.println(m.getName()));

          //then
          assertThat(result.size()).isEqualTo(2);
       }

       @Test
       public void aggregation() throws Exception {

           //given
           List<Tuple> result = queryFactory.select(member.count(),
                   member.age.sum(),
                   member.age.avg(),
                   member.age.max(),
                   member.age.min())
                   .from(member)
                   .fetch();

           //when
            Tuple tuple = result.get(0);
           //then
           assertThat(tuple.get(member.count())).isEqualTo(3);
           assertThat(tuple.get(member.age.sum())).isEqualTo(90);
           assertThat(tuple.get(member.age.avg())).isEqualTo(30);
        }
}
