package com.mindhub.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserExceptions.class)
    public ResponseEntity<String> userExceptionHandler(UserExceptions userExceptions){
        return new ResponseEntity<>(userExceptions.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskExceptions.class)
    public ResponseEntity<String> taskExceptionHandler(TaskExceptions taskExceptions){
        return new ResponseEntity<>(taskExceptions.getMessage(), HttpStatus.BAD_REQUEST);
    }
}