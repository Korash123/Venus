package com.example.Venus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sunil Phuyal
 * @version v1.0
 * @since 12/13/2024
 */

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends AppException {
    public static final String BAD_REQUEST = "bad.request";

    public BadRequestException(String errorKey, String[] params) {
        super(errorKey, params, HttpStatus.NOT_FOUND);
    }

    public BadRequestException(String[] params) {
        super(BAD_REQUEST, params, HttpStatus.NOT_FOUND);
    }

    public BadRequestException(String errorKey) {
        this(errorKey, new String[]{});
    }

}
