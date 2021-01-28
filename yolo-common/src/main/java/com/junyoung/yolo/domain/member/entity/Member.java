package com.junyoung.yolo.domain.member.entity;

import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    private String id;
    private String name;
    private int age;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<TodoItem> todoItemList = new ArrayList<>();

    @Builder
    public Member(String name, int age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
    }

    public static Member create(String name, int age) {
        return Member.builder()
                     .name(name)
                     .age(age)
                     .build();
    }

    public void saveItem(TodoItem todoItem) {
        this.todoItemList.add(todoItem);
        todoItem.setMember(this);
    }
}
