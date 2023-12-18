package com.projectLearn.TaskList.advice;

import com.projectLearn.TaskList.exceptions.AlreadyExistException;
import com.projectLearn.TaskList.model.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAlreadyExistException(AlreadyExistException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage("User already registered");
        response.setStatusCode(HttpStatus.FORBIDDEN.value());
        response.setTimestamp(LocalDateTime.now().toString());
        return  new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAlreadyExistException(RuntimeException ex){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDateTime.now().toString());
        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


}
