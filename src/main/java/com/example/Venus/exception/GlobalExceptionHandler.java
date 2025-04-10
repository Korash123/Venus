package com.example.Venus.exception;

import com.example.Venus.dto.global.ErrorWrapper;
import com.example.Venus.dto.global.Error;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.utils.MessageSourceUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    @created 30/08/2024 5:39 PM
    @project venus
    @author korash.waiba
*/
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSourceUtils messageSourceUtils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUserInvalidException(Exception ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), new ErrorWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<ErrorWrapper> errorWrapperList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ErrorWrapper errorWrapper = new ErrorWrapper();
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
            errorWrapper.setErrorMessage(error.getDefaultMessage());
            errorWrapper.setErrorCode(HttpStatus.BAD_REQUEST.value());
            errorWrapperList.add(errorWrapper);
        });
        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), errorWrapperList);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }


    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("JSON Processing error: ", ex);
        ErrorWrapper errorWrapper = new ErrorWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error processing JSON: " + ex.getMessage());
        return composeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorWrapper);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<GlobalApiResponse<?>> handleGenericException(GenericException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        String serverErrorKey = "Something went Unauthorized. Please Check it. ";
        Error error = new Error(Collections.singletonList(new ErrorWrapper(httpStatus.value(),
                serverErrorKey)));
        GlobalApiResponse<?> response = new GlobalApiResponse<>()
                .setError(error)
                .setTimestamp(LocalDateTime.now())
                .setMessage(serverErrorKey)
                .setResponseCode(String.valueOf(httpStatus.value()));
        return new ResponseEntity<>(response, httpStatus);

    }

//    @ExceptionHandler(ImageProcessingException.class)
//    public ResponseEntity<?> handleImageProcessingException(ImageProcessingException ex) {
//        log.error(ex.getMessage());
//        ex.printStackTrace();
//        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(HttpStatus.BAD_REQUEST.value(), ex.toString()));
//    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
//        log.error(ex.getMessage());
//        log.error(ex.getMessage());
//        ex.printStackTrace();
//        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(HttpStatus.BAD_REQUEST.value(), "Invalid arguments provided."));
//    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrationViolation(DataIntegrityViolationException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(HttpStatus.BAD_REQUEST.value(), messageSourceUtils.getMessage("data.already.exists")));
    }

//    @ExceptionHandler(InvalidApiKeyException.class)
//    public ResponseEntity<?> handleMismatchOtpException(InvalidApiKeyException ex){
//        log.error(ex.getMsg());
//        ex.printStackTrace();
//        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(ex.getCode(), ex.getMsg()));
//    }

//    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
//    public ResponseEntity<?> handleNotFoundException(ChangeSetPersister.NotFoundException ex){
//        log.error(ex.getMessage());
//        ex.printStackTrace();
//        return composeResponseEntity(HttpStatus.NOT_FOUND.value(), new ErrorWrapper(ex.getCode(), ex.getMsg()));
//    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        ErrorWrapper errorWrapper = new ErrorWrapper(
                ex.getHttpStatus().value(),
                ex.getMessage()
        );

        Error error = new Error(Collections.singletonList(errorWrapper));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.UNAUTHORIZED.value(), new ErrorWrapper(HttpStatus.UNAUTHORIZED.value(), messageSourceUtils.getMessage("invalid.request", new Object[]{"Token"})));
    }


    @ExceptionHandler(ProcResponseException.class)
    public ResponseEntity<?> handleProcResposneException(ProcResponseException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        ErrorWrapper errorWrapper = new ErrorWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error processing JSON: " + ex.getMessage());
        return composeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorWrapper);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.UNAUTHORIZED.value(), new ErrorWrapper(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<?> handleUnsupportedEncodingException(UnsupportedEncodingException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(HttpStatus.BAD_REQUEST.value(),  ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleUserInvalidException(IllegalStateException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.BAD_REQUEST.value(), new ErrorWrapper(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleException(ExpiredJwtException ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return composeResponseEntity(HttpStatus.UNAUTHORIZED.value(), new ErrorWrapper(HttpStatus.UNAUTHORIZED.value(),  "Token has expired.")); //TODO: MAke it dynamic
    }

    private ResponseEntity<?> composeResponseEntity(int responseCode, ErrorWrapper... errorWrappers){
        return new ResponseEntity<>(
                GlobalApiResponse
                        .builder()
                        .error(new Error(List.of(errorWrappers)))
                        .timestamp(LocalDateTime.now())
                        .responseCode(String.valueOf(responseCode))
                        .build()
                ,HttpStatus.OK);
    }

    private ResponseEntity<?> composeResponseEntity(int responseCode, List<ErrorWrapper> errorWrappers){
        return new ResponseEntity<>(
                GlobalApiResponse
                        .builder()
                        .error(new Error(errorWrappers))
                        .timestamp(LocalDateTime.now())
                        .responseCode(String.valueOf(responseCode))
                        .build()
                , HttpStatus.OK);
    }


    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<String> handleTokenNotFoundException(TokenNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Token not found.");
    }


}

