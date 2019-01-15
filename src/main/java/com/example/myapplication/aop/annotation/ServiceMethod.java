package com.example.myapplication.aop.annotation;

import com.example.myapplication.aop.ServiceExceptionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceMethod {
    ServiceExceptionType exceptionType() default ServiceExceptionType.UNKNOWN_SERVICE_EXCEPTION;
}
