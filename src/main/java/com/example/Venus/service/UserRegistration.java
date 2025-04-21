package com.example.Venus.service;

import com.example.Venus.dto.request.ForgotPasswordRequest;
import com.example.Venus.dto.request.LogoutDto;
import com.example.Venus.dto.request.ProfilePictureRequestDto;
import com.example.Venus.dto.request.RegisterRequestDto;
import com.example.Venus.dto.response.AuthLogResponse;
import com.example.Venus.dto.response.ProfilePictureResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface UserRegistration {
    void registerUser(RegisterRequestDto registerRequestDto) throws Exception;
    AuthLogResponse resetPassword(String encryptedData, String newPassword) throws Exception;
    AuthLogResponse loginUser(RegisterRequestDto requestDto) throws JsonProcessingException;
    AuthLogResponse refreshAccessToken(String refreshToken) throws JsonProcessingException;
    void logout(LogoutDto logoutDto);
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception;
    void createOrUpdatedProfilePicture(ProfilePictureRequestDto profilePictureRequestDto) throws IOException;
    ProfilePictureResponseDto getProfilePicture() throws Exception;
    void deleteProfilePicture() throws Exception;
//  public void validateResetLink(ValidateRspwdData validateRspwdData) throws JsonProcessingException;

}
