package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.dto.MemberReponseDto;
import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.todoItem.entity.QTodoItem;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        TodoItem todoItem = TodoItem.create("helloWorld", false);
        TodoItem todoItem2 = TodoItem.create("투두리스트 조회!!", false);
        em.persist(todoItem);
        em.persist(todoItem2);
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
        for (MemberReponseDto dto : collect) {
            System.out.println(dto.getEmail());
            System.out.println(dto.getTodoItems().get(0).getText());
        }
    }

    @Test
    public void lookUpTodoItemByDate() throws Exception {
        LocalDate localDate = LocalDate.parse("2021-02-10", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime startDate = localDate.atStartOfDay();
        LocalDateTime endDate = LocalDateTime.of(localDate, LocalTime.of(23, 59, 59));

        System.out.println(startDate);
        System.out.println(endDate);

     List<TodoItem> todoItems = queryFactory
                .selectFrom(QTodoItem.todoItem)
                .where(QTodoItem.todoItem.startDate.between(startDate, endDate).and(todoItem.text.eq("helloWorld")))
                .fetch();

      todoItems.forEach(todo -> {
            System.out.println(todo.getText());
        });
    }
}
