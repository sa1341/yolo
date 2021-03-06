package com.junyoung.yolo.domain.todo.controller;

import com.junyoung.yolo.domain.todo.service.TodoService;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import com.junyoung.yolo.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.junyoung.yolo.domain.todo.service.TodoServiceHelper.checkStartAndEndDate;
import static com.junyoung.yolo.domain.todo.service.TodoServiceHelper.validateTodoItemRequest;


@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoService todoService;

    /**
     * TodoItem 엔티티의 LifeCycle을 관장하는 Restful API 입니다.
     */
    @GetMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<TodoItemResponse>> fetchTodoItems() {
        List<TodoItemResponse> todoItemResponses = todoService.fetchTodoItems();
        return new ResponseEntity<>(todoItemResponses, HttpStatus.OK);
    }

    @GetMapping(value = "/todos/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<TodoItemResponse>> findTodoItemsByDate(
            @PathVariable final String id,
            @RequestParam(name = "selectedStartDate") final String selectedStartDate,
            @RequestParam(name = "selectedEndDate") final String selectedEndDate) {
        logger.info("memberId: {}", id);
        logger.info("selectedStartDate: {}", selectedStartDate);
        logger.info("selectedEndDate: {}", selectedEndDate);
        checkStartAndEndDate(selectedStartDate, selectedEndDate);
        List<TodoItemResponse> todoItemResponses = todoService.findTodoItemsByDate(id, selectedStartDate, selectedEndDate);
        return new ResponseEntity<>(todoItemResponses, HttpStatus.OK);
    }

    @PostMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<TodoItemResponse> saveTodoItem(@RequestBody final TodoItemRequest todoItemRequest) {
        validateTodoItemRequest(todoItemRequest);
        TodoItemResponse todoItemResponse = todoService.saveTodoItem(todoItemRequest);
        return new ResponseEntity<>(todoItemResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateTodoItem(@RequestBody final String todoItem) {
        TodoItemRequest todoItemRequest = GsonUtil.changeTodoItemRequest(todoItem);
        String updatedId = todoService.changeTodoItem(todoItemRequest);
        return new ResponseEntity<>(updatedId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/todos/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> deleteTodoItemById(@PathVariable final String id) {
        logger.info("TodoItem Id: {}", id);
        todoService.deleteTodoItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
