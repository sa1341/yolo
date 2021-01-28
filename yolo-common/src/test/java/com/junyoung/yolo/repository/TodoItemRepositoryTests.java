package com.junyoung.yolo.repository;

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
import java.util.List;

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

        Member member = Member.create("junyoung", 30);
        TodoItem todoItem1 = TodoItem.create("아 인생 잣 같네");
        TodoItem todoItem2= TodoItem.create("성공합시다!!!");
        TodoItem todoItem3= TodoItem.create("내일은 드디어 불금이다!!");

        member.saveItem(todoItem1);
        member.saveItem(todoItem2);
        member.saveItem(todoItem3);
        em.persist(member);
    }


    @Test
    public void paging2() throws Exception {

        //given
        Member member = queryFactory.select(QMember.member)
                .from(QMember.member)
                .fetchOne();

        //when
        List<TodoItem> todoItemList = member.getTodoItemList();

        todoItemList.forEach(todo -> System.out.println(todo.getContent()));
        //then
     }



}
