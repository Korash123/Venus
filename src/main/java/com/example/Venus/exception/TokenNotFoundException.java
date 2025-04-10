package com.example.Venus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends AppException {
    public static final String TOKEN_NOT_FOUND = "token.not.found";

    public TokenNotFoundException(String errorKey, String[] params) {
        super(errorKey, params, HttpStatus.NOT_FOUND);
    }

    public TokenNotFoundException(String[] params) {
        super(TOKEN_NOT_FOUND, params, HttpStatus.NOT_FOUND);
    }

    public TokenNotFoundException(String errorKey) {
        this(errorKey, new String[]{});
    }
}