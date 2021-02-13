package com.junyoung.yolo.domain.todoItem.entity;

import com.junyoung.yolo.domain.BaseTimeEntity;
import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TodoItem extends BaseTimeEntity {

    @Id
    private String id;

    private String text;

    private boolean isDone;

    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected TodoItem(String text, boolean isDone) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.isDone = isDone;
    }

    public static TodoItem create(String text, boolean isDone) {
        return new TodoItem(text, isDone);
    }

    public void changeContent(String content) {
        this.text = content;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public TodoItemResponse changeTodoResponse() {
        return new TodoItemResponse(this.id, this.text, this.isDone);
    }
}
