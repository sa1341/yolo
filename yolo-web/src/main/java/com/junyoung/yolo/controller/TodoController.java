package com.junyoung.yolo.controller;

import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import com.junyoung.yolo.domain.todoItem.service.TodoService;
import com.junyoung.yolo.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.junyoung.yolo.domain.todoItem.service.TodoServiceHelper.validateTodoItemRequest;

@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    /**
     * TodoItem 엔티티의 LifeCycle을 관장하는 Restful API 입니다.
     */

    @GetMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<TodoItemResponse>> fetchTodoItems() {
        List<TodoItemResponse> todoItemResponses = todoService.fetchTodoItems();
        return new ResponseEntity<>(todoItemResponses, HttpStatus.OK);
    }

    @PostMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> saveTodoItem(@RequestBody final TodoItemRequest todoItemRequest) {
        validateTodoItemRequest(todoItemRequest);
        todoService.saveTodoItem(todoItemRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateTodoItem(@RequestBody final String todoItem) {
        TodoItemRequest todoItemRequest  = GsonUtil.changeTodoItemRequest(todoItem);
        String updatedId = todoService.changeTodoItem(todoItemRequest);
        return new ResponseEntity<>(updatedId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/todos/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> deleteTodoItem(@PathVariable final String id) {
        todoService.deleteTodoItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
