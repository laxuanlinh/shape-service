package com.linhlx.shapeservice.exception;

public class UserException extends RuntimeException {

    private String message;

    public UserException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
