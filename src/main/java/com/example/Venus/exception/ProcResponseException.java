package com.example.Venus.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProcResponseException extends AppException {
    public static final String PROC_NOT_FOUND = "proc.not.found";

    public ProcResponseException(String errorKey, String[] params) {
        super(errorKey, params, HttpStatus.NOT_FOUND);
    }

    public ProcResponseException(String[] params) {
        super(PROC_NOT_FOUND, params, HttpStatus.NOT_FOUND);
    }

    public ProcResponseException(String errorKey) {
        this(errorKey, new String[]{});
    }
}
