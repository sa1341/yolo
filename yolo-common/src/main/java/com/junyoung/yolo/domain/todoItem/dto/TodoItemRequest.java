package com.junyoung.yolo.domain.todoItem.dto;

import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import lombok.Getter;

@Getter
public class TodoItemRequest {

    private Long id;
    private String text;
    private boolean isDone;

    public TodoItem toEntity() {
        return TodoItem.create(id, text, isDone);
    }
}
