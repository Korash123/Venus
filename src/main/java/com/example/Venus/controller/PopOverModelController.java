package com.example.Venus.controller;

import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiRequest;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.PopOVerModelRequestDto;
import com.example.Venus.dto.request.ProfilePictureRequestDto;
import com.example.Venus.dto.response.EventNewResponseDto;
import com.example.Venus.dto.response.PopOverModelResponseDto;
import com.example.Venus.service.PopOverModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(URL_CONSTANTS.PopOverModel.POP_OVER_MODEL_URL)
@RequiredArgsConstructor
public class PopOverModelController extends BaseController {
    private final PopOverModelService popOverModelService;


    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> create(
            @ModelAttribute PopOVerModelRequestDto requestDto) throws IOException {
        popOverModelService.create(requestDto);
        return getSuccessResponse("pop.created.success", HttpStatus.CREATED);
    }


    @GetMapping(URL_CONSTANTS.COMMON.GET_ALL)
    public GlobalApiResponse<?> showPopOverModel() throws Exception {
        List<PopOverModelResponseDto> popOverModelResponseDtos =  popOverModelService.showPopOverModel();
        return getSuccessResponse("pop.over.get.success",popOverModelResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping(URL_CONSTANTS.COMMON.DELETE_BY_ID)
    public GlobalApiResponse<?> deletePopOverImage(@RequestBody GlobalApiRequest<Long> request) {
        popOverModelService.deletePopOverImage(request.getData());
        return getSuccessResponse("pop.over.delete.success", "popOver deleted successfully", HttpStatus.NO_CONTENT);
    }

}
