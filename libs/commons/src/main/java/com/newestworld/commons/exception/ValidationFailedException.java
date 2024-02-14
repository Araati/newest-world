package com.newestworld.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationFailedException extends RuntimeException {

    public ValidationFailedException(final String message){
        super(message);
    }

    public ValidationFailedException(final String message, final Object... params){
        super(String.format(message, params));
    }

    public ValidationFailedException(){
        super("Field validation failed");
    }

    public ValidationFailedException(final String name, final String field){
        this("Field %s validation failed", name, field);
    }
}
