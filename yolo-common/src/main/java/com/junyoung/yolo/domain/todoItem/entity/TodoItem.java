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
import java.util.UUID;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TodoItem {

    @Id
    private String id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    protected TodoItem(String content) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
    }

    public static TodoItem create(String content) {
        return TodoItem.builder()
                    .content(content)
                    .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
