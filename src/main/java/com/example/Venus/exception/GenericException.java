package com.example.Venus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class GenericException extends AppException {
    public static final String GENERIC_UNAUTHORIZED = "invalid.credentials";

    public GenericException(String errorKey, String[] params) {
        super(errorKey, params, HttpStatus.NOT_FOUND);
    }

    public GenericException(String[] params) {
        super(GENERIC_UNAUTHORIZED, params, HttpStatus.NOT_FOUND);
    }

    public GenericException(String errorKey) {
        this(errorKey, new String[]{});
    }

}
