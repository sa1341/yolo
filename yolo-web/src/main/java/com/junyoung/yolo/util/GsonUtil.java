package com.junyoung.yolo.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;

import java.lang.reflect.Type;

public class GsonUtil {

    public static TodoItemRequest changeTodoItemRequest(String todoItem) {
        Type type = new TypeToken<TodoItemRequest>() {}.getType();
        Gson gson = new Gson();
        TodoItemRequest todoItemRequest = gson.fromJson(todoItem, type);
        return todoItemRequest;
    }
}
