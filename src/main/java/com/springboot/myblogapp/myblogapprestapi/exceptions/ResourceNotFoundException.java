package com.springboot.myblogapp.myblogapprestapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long id) {
        super(String.format("%s not found with %s :'%s'", resourceName, fieldName, id));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = id;
    }

    public String getresourceName() {
        return resourceName;
    }

    public String fieldName() {
        return fieldName;
    }

    public long getfieldValue() {
        return fieldValue;
    }
}
