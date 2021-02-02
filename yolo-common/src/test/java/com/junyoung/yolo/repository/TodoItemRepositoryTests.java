package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.dto.MemberReponseDto;
import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.junyoung.yolo.domain.member.entity.QMember.member;
import static com.junyoung.yolo.domain.todoItem.entity.QTodoItem.todoItem;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoItemRepositoryTests {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @Before
    public void before() {

        queryFactory = new JPAQueryFactory(em);

        Member member1 = Member.create("junyoung", 30);
        Member member2 = Member.create("minwan", 30);
        Member member3 = Member.create("seungtop", 30);

        TodoItem todoItem1 = TodoItem.create("아 인생 잣 같네");
        TodoItem todoItem2= TodoItem.create("성공합시다!!!");
        TodoItem todoItem3= TodoItem.create("내일은 드디어 불금이다!!");

        member1.saveItem(todoItem1);
        member2.saveItem(todoItem2);
        member3.saveItem(todoItem3);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
    }

    /**
     * Querydsl에서 제공하는 Result Aggregation (결과 집합)을 사용하는 예제 코드입니다.
     * 결과집합은 Querydsl의 결과를 특정 키를 기준 삼아 그룹화 하는 것을 의미함.00
     *
     * @throws Exception
     */

    @Test
    public void resultAggregationTest() throws Exception {

        //given
        Map<Member, List<TodoItem>> transform = queryFactory
                .selectFrom(member)
                .leftJoin(member.todoItemList, todoItem)
                .transform(groupBy(member).as(list(todoItem)));

        //when
        List<MemberReponseDto> collect = transform.entrySet().stream()
                .map(entry -> new MemberReponseDto(entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.toList());

        //then
        for (MemberReponseDto dto: collect) {
            System.out.println(dto.getEmail());
            System.out.println(dto.getTodoItems().get(0).getContent());
        }
     }
}
