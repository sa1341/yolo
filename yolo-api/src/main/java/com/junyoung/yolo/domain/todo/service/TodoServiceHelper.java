package com.junyoung.yolo.domain.todo.service;

import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.exception.TodoItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class TodoServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceHelper.class);

    public static void validateTodoItemRequest(TodoItemRequest todoItemRequest) {

        if (todoItemRequest == null || StringUtils.isEmpty(todoItemRequest.getMemberId())) {
            throw new TodoItemNotFoundException("TodoItem params is necessary");
        }

        logger.debug("TodoItemRequest: {}", todoItemRequest);
    }

    public static void checkStartAndEndDate(final String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            throw new IllegalArgumentException("startDate And EndDate is necessary");
        }
    }
}
