package com.rest.springbootemployee.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String className) {
        super(className + " Not found!.");
    }
}
