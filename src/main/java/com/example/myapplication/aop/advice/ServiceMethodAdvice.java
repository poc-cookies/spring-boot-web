package com.example.myapplication.aop.advice;

import com.example.myapplication.AppProperties;
import com.example.myapplication.aop.ServiceException;
import com.example.myapplication.aop.ServiceExceptionType;
import com.example.myapplication.aop.annotation.ServiceMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceMethodAdvice {

    private final AppProperties appProperties;

    @Autowired
    public ServiceMethodAdvice(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Around("@annotation(com.example.myapplication.aop.annotation.ServiceMethod) && execution(public * *(..))")
    public Object serviceMethod(final ProceedingJoinPoint proceedingJoinPoint) throws ServiceException {

        Object value;
        final MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        final ServiceMethod serviceMethod = signature.getMethod().getAnnotation(ServiceMethod.class);

        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable ex) {
            final ServiceExceptionType exceptionType = serviceMethod.exceptionType();
            log.error("{} : App version: {}", exceptionType.getMessage(), this.appProperties.getVersion(), ex);
            throw ServiceException.create(exceptionType, ex);
        }

        return value;
    }
}
