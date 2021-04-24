package com.junyoung.yolo.domain.member.dto;

import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class MemberReponseDto {

    private String email;
    private List<TodoItem> todoItems = new ArrayList<TodoItem>();

}
