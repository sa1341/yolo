package com.junyoung.yolo.domain.member.entity;

import com.junyoung.yolo.domain.BaseTimeEntity;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<TodoItem> todoItemList = new ArrayList<>();

    @Builder
    public Member(String id ,String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public static Member create(String id, String name, int age) {
        return Member.builder()
                     .id(id)
                     .name(name)
                     .age(age)
                     .build();
    }

    public void saveItem(TodoItem todoItem) {
        this.todoItemList.add(todoItem);
        todoItem.setMember(this);
    }

    public void deleteTodoItem(TodoItem todoItem) {
        this.getTodoItemList().remove(todoItem);
        todoItem.setMember(null);
    }
}
