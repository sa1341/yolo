package com.junyoung.yolo.domain.todoItem.entity;

import com.junyoung.yolo.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    protected TodoItem(Long id, String text, boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }

    public static TodoItem create(Long id, String text, boolean isDone) {
        return TodoItem.builder()
                    .id(id)
                    .text(text)
                    .isDone(isDone)
                    .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
