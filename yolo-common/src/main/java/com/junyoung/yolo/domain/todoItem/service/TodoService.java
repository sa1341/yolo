package com.junyoung.yolo.domain.todoItem.service;

import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.junyoung.yolo.domain.todoItem.repository.TodoItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class TodoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoItemRepository todoItemRepository;

    public void saveTodoItemList(List<TodoItemRequest> todoItemsRequest) {
        List<TodoItem> todoItemList = todoItemsRequest.stream()
                .map(TodoItemRequest::toEntity)
                .collect(Collectors.toList());
        todoItemRepository.saveAll(todoItemList);
        logger.info("saving todoItem is success");
    }

    public List<TodoItemResponse> fetchAllTodoItemList() {
        return null;
    }
}
