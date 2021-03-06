package com.junyoung.yolo.domain.todoItem.dto;

import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class TodoItemRequest {

    private String id;
    private String text;
    private boolean isDone;
    private String memberId;

    public TodoItem toEntity() {
        return TodoItem.create(text, isDone);
    }
}
