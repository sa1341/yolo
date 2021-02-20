package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.entity.Member;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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

        Member member1 = Member.create("sa1341","junyoung", 30);
        Member member2 = Member.create("syn7714","minwan", 30);
        Member member3 = Member.create("syn1341","seungtop", 30);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
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
