package com.junyoung.yolo.domain.todoItem.service;

import com.junyoung.yolo.domain.LocalDateParser;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import com.junyoung.yolo.domain.todoItem.entity.TodoItem;
import com.junyoung.yolo.domain.todoItem.repository.TodoItemRepository;
import com.junyoung.yolo.domain.todoItem.repository.TodoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class TodoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoItemRepository todoItemRepository;
    private final TodoJpaRepository todoJpaRepository;

    public void saveTodoItem(TodoItemRequest todoItemRequest) {
        TodoItem todoItem = todoItemRequest.toEntity();
        todoItemRepository.save(todoItem);
        logger.info("saving TodoItem is success");
    }

    @Transactional(readOnly = true)
    public List<TodoItemResponse> fetchTodoItems() {
        List<TodoItem> todoItems = todoItemRepository.findAll();
        List<TodoItemResponse> todoResponse = todoItems.stream()
                .map(TodoItem::changeTodoResponse)
                .collect(Collectors.toList());
        return todoResponse;
    }

    @Transactional(readOnly = true)
    public List<TodoItemResponse> fetchTodoItemsByDate(String date) {
        LocalDateParser localDateParser = new LocalDateParser(date);
        LocalDateTime startDate = localDateParser.startDate();
        LocalDateTime endDate = localDateParser.endDate();

        List<TodoItem> todoItems = todoJpaRepository.fetchTodoItemByDate(startDate, endDate);
        List<TodoItemResponse> todoResponse = todoItems.stream()
                .map(TodoItem::changeTodoResponse)
                .collect(Collectors.toList());

        return todoResponse;
    }

    public void deleteTodoItem(String id) {
        logger.warn("deleted TodoItemId: {}", id);
        todoItemRepository.deleteById(Long.parseLong(id));
    }

    public String changeTodoItem(TodoItemRequest todoItemRequest) {
        TodoItem todoItem = todoItemRequest.toEntity();
        Long id = todoItem.getId();
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isPresent()) {
            TodoItem findTodoItem = optionalTodoItem.get();
            findTodoItem.changeContent(todoItem.getText());
        }
        return String.valueOf(id);
    }
}
