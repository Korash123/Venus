package com.example.Venus.controller;

import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.base.BaseController;
import com.example.Venus.dto.global.GlobalApiRequest;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.*;
import com.example.Venus.dto.response.AuthLogResponse;
import com.example.Venus.dto.response.ProfilePictureResponseDto;
import com.example.Venus.service.UserRegistration;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping(URL_CONSTANTS.UserRegistration.USER_BASE_URL)
@RequiredArgsConstructor
public class UserRegisterController extends BaseController {

    private final UserRegistration userRegistration;

    @PostMapping(URL_CONSTANTS.COMMON.REGISTER)
    public GlobalApiResponse<?> registerUser(@Valid @RequestBody GlobalApiRequest<RegisterRequestDto> request) throws Exception {
        userRegistration.registerUser(request.getData());
        return getSuccessResponse("user.register.success", HttpStatus.OK);
    }

    @PostMapping(URL_CONSTANTS.COMMON.LOGIN)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody GlobalApiRequest<RegisterRequestDto> authLogRequest) throws JsonProcessingException {
        AuthLogResponse authLogResponse = userRegistration.loginUser(authLogRequest.getData());

        return new ResponseEntity<>(getSuccessResponse("auth.success", new String[]{"Tokens generated successfully"}, authLogResponse, HttpStatus.OK),
                HttpStatus.OK);
    }

    @PostMapping(URL_CONSTANTS.COMMON.FORGOT_PASSWORD)
    public GlobalApiResponse<?> gorgotPassword(@RequestBody GlobalApiRequest<ForgotPasswordRequest> request) throws Exception {
        userRegistration.forgotPassword(request.getData());
        return getSuccessResponse("forgot.password",HttpStatus.OK);
    }

//    @PostMapping(URL_CONSTANTS.COMMON.RESET_PASSWORD)
//    public GlobalApiResponse<?> validateResetLink(@RequestBody GlobalApiRequest<ValidateRspwdData> authLogData) throws Exception {
//        userRegistration.validateResetLink(authLogData.getData());
//        return getSuccessResponse("password.reset.link.success",  HttpStatus.OK);
//    }

    @PostMapping(URL_CONSTANTS.COMMON.SET_PASSWORD)
    public GlobalApiResponse<AuthLogResponse> setNewPassword(@Valid @RequestBody GlobalApiRequest<PasswordResetRequest> request) throws Exception {
        AuthLogResponse response = userRegistration.resetPassword(request.getData().getEncryptedData(), request.getData().getNewPassword());
        return getSuccessResponse("password.set.success", response, HttpStatus.OK);
    }

    @PostMapping(URL_CONSTANTS.COMMON.LOGOUT)
    public GlobalApiResponse<?> logout(@RequestBody GlobalApiRequest<LogoutDto> logoutDto) throws Exception {
        userRegistration.logout(logoutDto.getData());
        return getSuccessResponse("auth.logout.success", HttpStatus.OK);
    }

    @PostMapping(URL_CONSTANTS.COMMON.REFRESH_TOKEN)
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws JsonProcessingException {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        AuthLogResponse refreshedTokens = userRegistration.refreshAccessToken(refreshToken);

        return new ResponseEntity<>(
                getSuccessResponse("auth.token.refresh.success", new String[]{"Tokens refreshed successfully"}, refreshedTokens, HttpStatus.OK),
                HttpStatus.OK
        );
    }


}

