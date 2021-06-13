package com.junyoung.yolo.domain.todoItem.repository;

import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.junyoung.yolo.domain.member.entity.QMember.member;
import static com.junyoung.yolo.domain.todoItem.entity.QTodoItem.todoItem;

@RequiredArgsConstructor
@Repository
public class TodoJpaRepository {

    private final JPAQueryFactory queryFactory;

    public List<TodoItem> fetchTodoItemByDate(String memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.select(todoItem)
                .from(todoItem)
                .join(todoItem.member, member)
                .where(todoItem.createdDate.between(startDate, endDate).and(todoItem.member.id.eq(memberId)))
                .fetch();
    }

    public TodoItem fetchTodoItemWithMember(String id) {
        return queryFactory.selectFrom(todoItem)
                .join(todoItem.member, member).fetchJoin()
                .where(todoItem.id.eq(id))
                .fetchOne();
    }
}
