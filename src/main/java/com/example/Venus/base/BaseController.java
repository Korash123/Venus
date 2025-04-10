package com.example.Venus.base;


import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.utils.MessageSourceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/*
    @created 4/07/2025 5:39 PM
    @project electro-point
    @author Korash.Waiba
*/

@Component
@RequiredArgsConstructor
public class BaseController {

    @Autowired
    private MessageSourceUtils messageSourceUtils;


    public <T> GlobalApiResponse<T> getSuccessResponse(String msgCode,
                                                       String[] parmas,
                                                       T data,
                                                       HttpStatus httpStatus) {
        String msg = messageSourceUtils.getMessage(msgCode, parmas);
        return new GlobalApiResponse<T>()
                .setData(data)
                .setTimestamp(LocalDateTime.now())
                .setMessage(msg)
                .setResponseCode(String.valueOf(httpStatus.value()));
    }

    public GlobalApiResponse<?> getSuccessResponse(String msgCode,
                                                   String[] params,
                                                   HttpStatus httpStatus) {
        String msg = messageSourceUtils.getMessage(msgCode, params);

        return new GlobalApiResponse<>()
                .setTimestamp(LocalDateTime.now())
                .setMessage(msg)
                .setResponseCode(String.valueOf(httpStatus.value()));
    }

    public <T> GlobalApiResponse<T> getSuccessResponse(String msgCode,
                                                       T data,
                                                       HttpStatus httpStatus) {
        String msg = messageSourceUtils.getMessage(msgCode);

        return new GlobalApiResponse<T>()
                .setData(data)
                .setTimestamp(LocalDateTime.now())
                .setMessage(msg)
                .setResponseCode(String.valueOf(httpStatus.value()));

    }

    public GlobalApiResponse<?> getSuccessResponse(String msgCode,
                                                   HttpStatus httpStatus) {
        String msg = messageSourceUtils.getMessage(msgCode);

        return new GlobalApiResponse<>()
                .setTimestamp(LocalDateTime.now())
                .setMessage(msg)
                .setResponseCode(String.valueOf(httpStatus.value()));

    }

    public <T> GlobalApiResponse<T> getSuccessResponse(String msgCode,
                                                       String[] params,
                                                       List<T> data,
                                                       HttpStatus httpStatus) {

        String msg = messageSourceUtils.getMessage(msgCode, params);

        return new GlobalApiResponse<T>()
                .setDatalist(data)
                .setTimestamp(LocalDateTime.now())
                .setMessage(msg)
                .setResponseCode(String.valueOf(httpStatus.value()));
    }

    public <T> GlobalApiResponse<T> getSuccessResponse(String msgCode,
                                                       List<T> data,
                                                       HttpStatus httpStatus) {
        String msg = messageSourceUtils.getMessage(msgCode);

        return new GlobalApiResponse<T>()
                .setDatalist(data)
                .setTimestamp(LocalDateTime.now())
                .setMessage(msg)
                .setResponseCode(String.valueOf(httpStatus.value()));
    }

    public <T> GlobalApiResponse<T> getSuccessResponse(String msgCode,
                                                       String[] params,
                                                       List<T> data,
                                                       HttpStatus httpStatus,
                                                       int totalRecords,
                                                       int totalPages) {

        String msg = messageSourceUtils.getMessage(msgCode, params);

        return new GlobalApiResponse<T>()
                .setDatalist(data)
                .setMessage(msg)
                .setTimestamp(LocalDateTime.now())
                .setTotalPages(totalPages)
                .setTotalRecords(totalRecords)
                .setResponseCode(String.valueOf(httpStatus.value()));

    }

    public GlobalApiResponse<?> getSuccessResponse(String msgCode,
                                                   String[] params,
                                                   HttpStatus httpStatus,
                                                   int totalRecords,
                                                   int totalPages) {

        String msg = messageSourceUtils.getMessage(msgCode, params);

        return new GlobalApiResponse<>()
                .setMessage(msg)
                .setTimestamp(LocalDateTime.now())
                .setTotalRecords(totalRecords)
                .setTotalPages(totalPages)
                .setResponseCode(String.valueOf(httpStatus.value()));
    }

    public <T> GlobalApiResponse<T> getSuccessResponse(String msgCode,
                                                       List<T> data,
                                                       HttpStatus httpStatus,
                                                       int totalRecords,
                                                       int totalPages) {
        String msg = messageSourceUtils.getMessage(msgCode);
        return new GlobalApiResponse<T>()
                .setDatalist(data)
                .setMessage(msg)
                .setTotalRecords(totalRecords)
                .setTotalPages(totalPages)
                .setResponseCode(String.valueOf(httpStatus.value()));
    }

    public <T> GlobalApiResponse<T> getErrorResponse(String msgCode, HttpStatus httpStatus ) {
        String msg = messageSourceUtils.getMessage(msgCode);

        return new GlobalApiResponse<T>()
                .setDatalist(null)
                .setMessage(msg)
                .setTotalRecords(0)
                .setTotalPages(0)
                .setResponseCode(String.valueOf(httpStatus.value()));
    }

}