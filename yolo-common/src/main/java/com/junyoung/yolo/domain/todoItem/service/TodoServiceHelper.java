package com.junyoung.yolo.domain.todoItem.service;

import com.junyoung.yolo.exception.TodosNotFoundException;
import org.springframework.util.StringUtils;

public final class TodoServiceHelper {
    public static void validateTodoItemsRequest(String todoItemsRequest) {
        if (!StringUtils.hasText(todoItemsRequest)) {
            throw new TodosNotFoundException("todoItemsRequest is necessary");
        }
    }
}
