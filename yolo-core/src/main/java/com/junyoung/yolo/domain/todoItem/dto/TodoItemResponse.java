package com.junyoung.yolo.domain.todoItem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoItemResponse {
    private String id;
    private String text;
    private boolean isDone;
    private String memberId;

    public TodoItemResponse(String id, String text, boolean isDone, String memberId) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.memberId = memberId;
    }
}
