package com.rest.springbootemployee.exception;

public class NotFoundCompany extends RuntimeException{
    public NotFoundCompany() {
        super("Not Found Company");
    }
}
