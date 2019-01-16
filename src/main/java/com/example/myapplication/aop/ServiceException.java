package com.example.myapplication.aop;

public class ServiceException extends Exception {

    static final long serialVersionUID = 1L;

    private ServiceExceptionType type;

    private ServiceException() {
        super();
    }

    private ServiceException(String message) {
        super(message);
    }

    private ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    private ServiceException(ServiceExceptionType type, Throwable cause) {
        this(type.getMessage(), cause);
        this.type = type;
    }

    public static ServiceException create(final ServiceExceptionType exceptionType, final Throwable cause) {
        return new ServiceException(exceptionType, cause);
    }

    public ServiceExceptionType getType() {
        return this.type;
    }
}
