package com.jdt13.hotel.exception;

public class ApiExceptionUnauthorized extends RuntimeException{
    public ApiExceptionUnauthorized(String message) {super (message);}
    public ApiExceptionUnauthorized(String message, Throwable cause) {super (message, cause);}
}
