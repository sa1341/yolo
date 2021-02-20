package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.member.dto.MemberReponseDto;
import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.todoItem.entity.QTodoItem;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.junyoung.yolo.domain.todoItem.repository.TodoItemRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodoItemRepositoryTests {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @Autowired
    private TodoItemRepository todoItemRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        Member member = Member.create("a7713", "jun", 25);
        TodoItem todoItem = TodoItem.create("helloWorld", false);
        TodoItem todoItem2 = TodoItem.create("투두리스트 조회!!", false);
        member.saveItem(todoItem);
        member.saveItem(todoItem2);
        em.persist(member);
    }

    @Test
    public void deleteTodoItem() throws Exception {

        //given
        Member newbee = queryFactory.selectFrom(member)
                .where(member.name.eq("newbee"))
                .fetchOne();
        //when
        List<TodoItem> todoItemList = newbee.getTodoItemList();


        TodoItem findTodoItem = todoItemList.stream()
                .filter(todos -> todos.getText().equals("helloWorld")).findFirst().get();

        System.out.println("result:" + findTodoItem.getText());
        //then

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
