package com.junyoung.yolo.domain.todoItem.service;

import com.junyoung.yolo.exception.TodosNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class TodoServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceHelper.class);

    public static void validateTodoItemRequest(String todoItemRequest) {
        if (!StringUtils.hasText(todoItemRequest)) {
            throw new TodosNotFoundException("todoItemsRequest is necessary");
        }
        logger.debug("todoItemRequest: {}", todoItemRequest);
    }

}
