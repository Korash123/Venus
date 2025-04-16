package com.example.Venus.controller;

import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.ProfilePictureRequestDto;
import com.example.Venus.dto.response.ProfilePictureResponseDto;
import com.example.Venus.service.UserRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(URL_CONSTANTS.ProfilePicture.USER_BASE_URL)
@RequiredArgsConstructor
public class ProfilePictureController extends BaseController {
    private final UserRegistration userRegistration;

    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> createOrUpdatedProfilePicture(
            @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException {
        ProfilePictureRequestDto requestDto = new ProfilePictureRequestDto();
        requestDto.setProfilePicture(profilePicture);
        userRegistration.createOrUpdatedProfilePicture(requestDto);
        return getSuccessResponse("profile.created.success", HttpStatus.CREATED);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_BY_ID)
    public GlobalApiResponse<ProfilePictureResponseDto> getProfilePicture() throws Exception {
        return getSuccessResponse(
                "profile.fetch.success",
                userRegistration.getProfilePicture(),
                HttpStatus.OK);
    }

    @DeleteMapping(URL_CONSTANTS.COMMON.DELETEBYID)
    public GlobalApiResponse<?> deleteProfilePicture() throws Exception {
        userRegistration.deleteProfilePicture();
        return getSuccessResponse("profile.deleted.success", HttpStatus.NO_CONTENT);
    }


}
