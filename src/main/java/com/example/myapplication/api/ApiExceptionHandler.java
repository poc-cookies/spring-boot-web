package com.example.myapplication.api;

import com.example.myapplication.AppProperties;
import com.example.myapplication.aop.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.example.myapplication.api")
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final AppProperties appProperties;

    @Autowired
    public ApiExceptionHandler(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<?> handleServiceException(ServiceException ex) {
        return handleException(HttpStatus.BAD_REQUEST, ex, "Service exception.");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<?> handleAll(Throwable ex) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, ex, "Internal server error.");
    }

    private ResponseEntity<?> handleException(HttpStatus status, Throwable ex, String error) {
        final String message = ex.getLocalizedMessage();
        log.error("{} : App version: {}", message, this.appProperties.getVersion(), ex);
        return new ResponseEntity<>(new ApiError(status, message, error), new HttpHeaders(), status);
    }
}
