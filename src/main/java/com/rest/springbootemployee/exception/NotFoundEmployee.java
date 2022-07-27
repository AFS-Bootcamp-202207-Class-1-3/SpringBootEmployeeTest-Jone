package com.rest.springbootemployee.exception;

public class NotFoundEmployee extends RuntimeException {
    public NotFoundEmployee() {
        super("Not found employee.");
    }
}
