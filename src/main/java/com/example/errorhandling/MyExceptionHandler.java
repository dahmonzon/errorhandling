package com.example.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({CustumException.class})
    public ResponseEntity<Error> myMessage(CustumException c){
        Error er = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(),c.getMessage());
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


