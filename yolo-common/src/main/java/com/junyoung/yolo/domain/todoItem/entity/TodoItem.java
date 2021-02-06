package com.junyoung.yolo.domain.todoItem.entity;

import com.junyoung.yolo.domain.member.entity.Member;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TodoItem {

    @Id
    private Long id;

    private String text;

    private boolean isDone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected TodoItem(Long id, String text, boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();;
        this.updatedAt = LocalDateTime.now();;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();;
    }

    public static TodoItem create(Long id, String text, boolean isDone) {
        return new TodoItem(id, text, isDone);
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
