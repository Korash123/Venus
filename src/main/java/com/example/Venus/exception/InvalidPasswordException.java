//package com.example.Venus.exception;
//
//import lombok.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
///**
// * @author korash waiba
// * @version v1.0
// * @since 12/13/2024
// */
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public class InvalidPasswordException extends AppException {
//
//    public static final String INVALID_PASSWORD = "invalid.password";
//
//    public InvalidPasswordException(String errorKey, String[] params) {
//        super(errorKey, params, HttpStatus.NOT_FOUND);
//    }
//
//    public InvalidPasswordException(String[] params) {
//        super(INVALID_PASSWORD, params, HttpStatus.NOT_FOUND);
//    }
//
//    public InvalidPasswordException(String errorKey) {
//        this(errorKey, new String[]{});
//    }
//
//
//}
