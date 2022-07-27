package com.rest.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundEmployee extends RuntimeException {
    public NotFoundEmployee() {
        super("Not found employee.");
    }
}
