package com.example.Venus.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends AppException {

    public static final String RESOURCE_NOT_FOUND = "resource.not.found";

    public ResourceNotFoundException(String errorKey, String[] params) {
        super(errorKey, params, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String[] params) {
        super(RESOURCE_NOT_FOUND, params, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String errorKey) {
        this(errorKey, new String[]{});
    }

}