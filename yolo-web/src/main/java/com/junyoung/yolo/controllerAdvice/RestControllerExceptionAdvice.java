package com.junyoung.yolo.controllerAdvice;

import com.junyoung.yolo.exception.TodosNotFoundException;
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

    @ExceptionHandler(value = TodosNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleTodoItemsNotFound(TodosNotFoundException e) {
        logger.error("saving todoList is failed", e);
        ErrorResponse responseError = new ErrorResponse(400, "TodoItems 값이 존재하지 않습니다.");
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
