package com.example.myapplication.api;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    static final long serialVersionUID = 1L;

    private final HttpStatus status;
    private final String message;
    private final String error;
}
