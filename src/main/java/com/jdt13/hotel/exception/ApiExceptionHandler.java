package com.jdt13.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException (ApiRequestException e){
        ApiException1 apiException = new ApiException1 (
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ApiExceptionNoContent.class})
    public ResponseEntity<Object> exceptionResponseNoContent (ApiExceptionNoContent e){
        ApiException1 apiException = new ApiException1 (
                e.getMessage(),
                HttpStatus.NO_CONTENT,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = {ApiExceptionNotFound.class})
    public ResponseEntity<Object> exceptionResponseNotFound (ApiExceptionNotFound e){
        ApiException1 apiException = new ApiException1 (
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}
