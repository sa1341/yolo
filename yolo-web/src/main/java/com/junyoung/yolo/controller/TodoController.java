package com.junyoung.yolo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemResponse;
import com.junyoung.yolo.domain.todoItem.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

import static com.junyoung.yolo.domain.todoItem.service.TodoServiceHelper.validateTodoItemRequest;

@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    /**
     * 사용자가 작성한 TodoItemList 저장 API
     *
     * @param todoItem
     * @return
     */
    @PostMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> saveTodoItem(@RequestBody final String todoItem) {
        validateTodoItemRequest(todoItem);
        Type type = new TypeToken<TodoItemRequest>() {}.getType();
        Gson gson = new Gson();
        TodoItemRequest todoItemRequest = gson.fromJson(todoItem, type);
        todoService.saveTodoItem(todoItemRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<TodoItemResponse>> fetchTodoItems() {
        List<TodoItemResponse> todoItemResponses = todoService.fetchAllTodoItems();
        return new ResponseEntity<>(todoItemResponses, HttpStatus.OK);
    }

    @PutMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateTodoItem(@RequestBody final String todoItem) {
        Type type = new TypeToken<TodoItemRequest>() {}.getType();
        Gson gson = new Gson();
        TodoItemRequest todoItemRequest  = gson.fromJson(todoItem, type);
        String updatedId = todoService.changeTodoItem(todoItemRequest);
        return new ResponseEntity<>(updatedId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/todos/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> deleteTodoItem(@PathVariable String id) {
        todoService.deleteTodoItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
