package com.tweet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tweet.payloads.ApiResponse;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message=ex.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public   ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String ,String >resp=new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error )->{
          String fieldName=  ((FieldError)error).getField();
            String message=error.getDefaultMessage();
            resp.put(fieldName, message);
        } );

        return new ResponseEntity<Map<String,String >>(resp,BAD_REQUEST);
    }
    //custom exception handler
}
