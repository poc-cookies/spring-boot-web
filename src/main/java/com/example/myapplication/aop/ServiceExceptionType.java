package com.example.myapplication.aop;

public enum ServiceExceptionType {

    UNKNOWN_SERVICE_EXCEPTION("Unknown service exception"),
    CANNOT_GET_ITEMS_BY_ID("Cannot get items by id"),
    CANNOT_GET_ALL_ITEMS("Cannot get all items");

    private final String message;

    ServiceExceptionType(final String message) {
        this.message = message;
    }

    public ServiceException createException(Throwable cause) {
        return new ServiceException(message, cause);
    }

    public String getMessage() {
        return message;
    }
}
