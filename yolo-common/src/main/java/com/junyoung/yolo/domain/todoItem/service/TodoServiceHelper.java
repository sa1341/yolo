package com.junyoung.yolo.domain.todoItem.service;

import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.exception.TodoItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TodoServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceHelper.class);

    public static void validateTodoItemRequest(TodoItemRequest todoItemRequest) {
        if (todoItemRequest == null) {
            throw new TodoItemNotFoundException("TodoItemRequest is necessary");
        }
        logger.debug("TodoItemRequest: {}", todoItemRequest);
    }
}
