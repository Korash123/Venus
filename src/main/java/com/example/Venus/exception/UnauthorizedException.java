package com.example.Venus.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@ResponseStatus(HttpStatus.UNAUTHORIZED)

public class UnauthorizedException extends AppException {
    public static final String UNAUTHORIZED_REQUEST = "unauthorized.request";

    public UnauthorizedException(String errorKey, String[] params) {
        super(errorKey, params, HttpStatus.NOT_FOUND);
    }

    public UnauthorizedException(String[] params) {
        super(UNAUTHORIZED_REQUEST, params, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String errorKey) {
        this(errorKey, new String[]{});
    }

}
