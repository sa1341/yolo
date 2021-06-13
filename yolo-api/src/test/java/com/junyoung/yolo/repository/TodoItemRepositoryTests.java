package com.junyoung.yolo.repository;

import com.junyoung.yolo.domain.LocalDateParser;
import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.member.entity.QMember;
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
import java.time.LocalDateTime;
import java.util.List;

import static com.junyoung.yolo.domain.member.entity.QMember.member;
import static com.junyoung.yolo.domain.todoItem.entity.QTodoItem.todoItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoItemRepositoryTests {

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager em;

    @Before
    public void setUp() {
        Member member = queryFactory.selectFrom(QMember.member)
                .where(QMember.member.id.eq("a79007714@gmail.com"))
                .fetchOne();

        TodoItem todoItem = TodoItem.create("벌써 일년", true);
        todoItem.setMember(member);
        em.persist(todoItem);
    }

    @Transactional
    @Test
    public void todoItem을_날짜조건으로_조회한다() throws Exception {
        //given
        String startDate = "2021-04-28";
        String endDate = "2021-04-28";
        String email ="a79007714@gmail.com";


        LocalDateTime localStartDate = LocalDateParser.parseStartDate(startDate);
        LocalDateTime localEndDate = LocalDateParser.parseEndDate(endDate);

        //when
        List<TodoItem> todoItemList = queryFactory.select(todoItem)
                .from(todoItem)
                .join(todoItem.member, member)
                .where(todoItem.createdDate.between(localStartDate, localEndDate).and(member.id.eq(email)))
                .fetch();

        //then
        todoItemList.forEach(todo -> System.out.println(todo.getText()));
     }
}
