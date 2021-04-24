package com.junyoung.yolo.controllerAdvice;

import com.junyoung.yolo.exception.TodoItemNotFoundException;
import com.junyoung.yolo.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestControllerExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = TodoItemNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleTodoItemsNotFound(TodoItemNotFoundException e) {
        logger.error("TodoItem is not exist", e);
        ErrorResponse responseError = new ErrorResponse(400, e.getMessage());
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
