package com.shpl.flightmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FlightBookingExceptionHandler {


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResult handleMethodArgumentNotValidException(final Throwable ex) {
        return ErrorResult.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getLocalizedMessage())
                .build();
    }
}
