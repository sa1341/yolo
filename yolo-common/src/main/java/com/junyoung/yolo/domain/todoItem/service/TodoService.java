package com.junyoung.yolo.domain.todoItem.service;

import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.junyoung.yolo.domain.todoItem.repository.TodoItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class TodoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoItemRepository todoItemRepository;

    public void saveTodoList(List<TodoItemRequest> todoItemRequests) {
       for (TodoItemRequest req: todoItemRequests) {
           TodoItem todoItem = req.toEntity();
           todoItemRepository.save(todoItem);
       }
       logger.info("saving todoItem is success!!");
    }

}
