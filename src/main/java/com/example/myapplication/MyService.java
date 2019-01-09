package com.example.myapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private final AppProperties appProperties;

    @Autowired
    public MyService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String getGreeting() {
        return "Hello! I'm " + this.appProperties.getName();
    }
}