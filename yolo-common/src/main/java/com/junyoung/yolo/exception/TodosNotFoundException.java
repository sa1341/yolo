package com.junyoung.yolo.exception;

public class TodosNotFoundException extends RuntimeException {
    public TodosNotFoundException() {
        super();
    }

    public TodosNotFoundException(String message) {
        super(message);
    }

    public TodosNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodosNotFoundException(Throwable cause) {
        super(cause);
    }
}
