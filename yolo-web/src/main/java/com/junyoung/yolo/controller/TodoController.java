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
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import java.util.List;
import static com.junyoung.yolo.domain.todoItem.service.TodoServiceHelper.validateTodoItemsRequest;

@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TodoService todoService;

    /**
     *
     * 사용자가 작성한 TodoItemList 저장 API
     *
     * @param todoItemList
     * @return
     */
    @PostMapping(value = "/todos", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> saveTodoItemList(@RequestBody final String todoItemList) {
        try {
            logger.info("todoItemList: {}", todoItemList);
            validateTodoItemsRequest(todoItemList);
            Type type = new TypeToken<List<TodoItemRequest>>() {}.getType();
            Gson gson = new Gson();
            List<TodoItemRequest> todoItemsRequests = gson.fromJson(todoItemList, type);
            todoService.saveTodoItemList(todoItemsRequests);
        } catch (Exception e) {
            logger.error("saving todoList is failed", e);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "/todos", produces = "applicationon/json; charset=UTF-8")
    public ResponseEntity<String> todoItemList() {
        try {


        } catch (Exception e) {
            logger.error("fetching todoItemList is failed", e);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
