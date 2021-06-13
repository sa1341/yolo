package com.junyoung.yolo.domain.member.entity;

import com.junyoung.yolo.domain.BaseTimeEntity;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    private String id;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<TodoItem> todoItemList = new ArrayList<>();

    @Builder
    public Member(String id ,String name, int age, MemberRole role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public void saveItem(TodoItem todoItem) {
        this.todoItemList.add(todoItem);
        todoItem.setMember(this);
    }

    public void deleteTodoItem(TodoItem todoItem) {
        this.getTodoItemList().remove(todoItem);
        todoItem.setMember(null);
    }


    public void addMemberRole(MemberRole memberRole) {
        this.role = memberRole;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
