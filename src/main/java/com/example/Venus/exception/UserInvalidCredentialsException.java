package com.example.Venus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInvalidCredentialsException extends RuntimeException {
    private int code;
    private String msg;


}
