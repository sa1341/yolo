package com.junyoung.yolo.domain.todoItem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoItemResponse {
    private String id;
    private String text;
    private boolean isDone;

    public TodoItemResponse(String id, String text, boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }
}
