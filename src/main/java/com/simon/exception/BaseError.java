package com.simon.exception;

public class BaseError {
    private String message;
    
    public BaseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
