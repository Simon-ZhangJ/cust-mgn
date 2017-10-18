package com.simon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simon.exception.BaseError;
import com.simon.exception.BaseException;
import com.simon.exception.DataNotFoundException;

@RestControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public BaseError handleNotFoundException(DataNotFoundException e) {
        return new BaseError(e.getMessage());
    }
    
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseError handleBaseException(BaseException e) {
        return new BaseError(e.getMessage());
    }
}
