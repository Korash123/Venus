package com.example.Venus.service;

import com.example.Venus.dto.request.ForgotPasswordRequest;
import com.example.Venus.dto.request.LogoutDto;
import com.example.Venus.dto.request.RegisterRequestDto;
import com.example.Venus.dto.response.AuthLogResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistration {
    void registerUser(RegisterRequestDto registerRequestDto) throws Exception;
    public AuthLogResponse resetPassword(String encryptedData, String newPassword) throws Exception;
    public AuthLogResponse loginUser(RegisterRequestDto requestDto) throws JsonProcessingException;
    public AuthLogResponse refreshAccessToken(String refreshToken) throws JsonProcessingException;
    public void logout(LogoutDto logoutDto);
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception;
//    public void validateResetLink(ValidateRspwdData validateRspwdData) throws JsonProcessingException;

}
