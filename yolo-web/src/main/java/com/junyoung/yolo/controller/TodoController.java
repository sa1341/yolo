package com.junyoung.yolo.controller;

import com.junyoung.yolo.domain.todoItem.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoService todoService;

    @PostMapping(value = "/v1/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> saveTodoList(@RequestBody String todoList) {

        try {
            logger.info("body: {}", todoList);
        } catch (Exception e) {
            logger.error("saving todoList is failed", e);
        }


        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
