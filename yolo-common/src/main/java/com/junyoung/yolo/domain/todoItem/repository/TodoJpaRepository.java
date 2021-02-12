package com.junyoung.yolo.domain.todoItem.repository;

import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.junyoung.yolo.domain.todoItem.entity.QTodoItem.todoItem;

@Repository
public class TodoJpaRepository {

    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public TodoJpaRepository(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    public List<TodoItem> fetchTodoItemByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.selectFrom(todoItem)
                .where(todoItem.createdDate.between(startDate, endDate))
                .fetch();
    }
}
