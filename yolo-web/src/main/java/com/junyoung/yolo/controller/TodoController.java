package com.junyoung.yolo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.junyoung.yolo.domain.todoItem.dto.TodoItemRequest;
import com.junyoung.yolo.domain.todoItem.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoService todoService;

    @PostMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> saveTodoList(@RequestBody String todoList) {

        try {
            logger.info("body: {}", todoList);
            Type type = new TypeToken<List<TodoItemRequest>>() {}.getType();
            Gson gson = new Gson();
            List<TodoItemRequest> todoItemRequests = gson.fromJson(todoList, type);
            todoItemRequests.forEach(todo -> logger.info(todo.getText()));
            todoService.saveTodoList(todoItemRequests);
        } catch (Exception e) {
            logger.error("saving todoList is failed", e);
        }


        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
