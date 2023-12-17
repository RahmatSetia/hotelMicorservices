package com.jdt13.hotel.exception;

public class ApiExceptionNotFound extends RuntimeException {
    public ApiExceptionNotFound(String message){super(message);}
    public ApiExceptionNotFound(String message, Throwable cause){super(message, cause);}
}
