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
    public ResponseEntity<?> handleServiceException(final ServiceException ex) {
        HttpStatus status;
        switch (ex.getType()) {
            case CANNOT_GET_ALL_ITEMS:
            case CANNOT_GET_ITEMS_BY_ID:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case UNKNOWN_SERVICE_EXCEPTION:
                status = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }
        return responseEntity(new ErrorResponse(status, ex.getType().getMessage(), ex.getLocalizedMessage()), status);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<?> handleAll(Throwable ex) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final String message = ex.getLocalizedMessage();
        log.error("{} : App version: {}", message, this.appProperties.getVersion(), ex);
        return responseEntity(new ErrorResponse(status, message, "Internal server error."), status);
    }

    private ResponseEntity<?> responseEntity(final ErrorResponse error, final HttpStatus status) {
        return new ResponseEntity<>(error, new HttpHeaders(), status);
    }
}
