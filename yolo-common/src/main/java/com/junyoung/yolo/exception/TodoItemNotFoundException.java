package com.junyoung.yolo.exception;

public class TodoItemNotFoundException extends RuntimeException {
    public TodoItemNotFoundException() {
        super();
    }

    public TodoItemNotFoundException(String message) {
        super(message);
    }

    public TodoItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
