package com.jdt13.hotel.exception;

public class ApiExceptionNoContent extends RuntimeException{
    public ApiExceptionNoContent(String message){super(message);}
    public ApiExceptionNoContent(String message, Throwable cause){super(message, cause);}
}
