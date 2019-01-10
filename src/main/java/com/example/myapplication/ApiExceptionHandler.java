package com.example.myapplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<?> handleServiceException(ServiceException ex) {
        return handleException(HttpStatus.BAD_REQUEST, ex, "Service exception.");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<?> handleAll(Throwable ex) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex, "Not implemented.");
    }

    private ResponseEntity<?> handleException(HttpStatus status, Throwable ex, String error) {
        final ApiError apiError = new ApiError(status, ex.getLocalizedMessage(), error);
        log.error(apiError.getMessage(), apiError);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
