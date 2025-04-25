package com.example.Venus.controller;

import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.BannerRequestDto;
import com.example.Venus.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(URL_CONSTANTS.Banners.BANNER_BASE_URL)
@RequiredArgsConstructor
public class BannerController extends BaseController {
    private final BannerService bannerService;

    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> createBanner(@ModelAttribute BannerRequestDto requestDto) throws Exception {
        bannerService.createBanner(requestDto);
        return getSuccessResponse("Banner.create", HttpStatus.OK);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_ALL)
    public GlobalApiResponse<?> getAllBanner() throws Exception {
        return getSuccessResponse("banners.get", bannerService.getAllBanner(),HttpStatus.OK);
    }

    @DeleteMapping(URL_CONSTANTS.COMMON.DELETE_BY_ID)
    public GlobalApiResponse<?> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return getSuccessResponse("banner.delete.success", HttpStatus.NO_CONTENT);
    }


}
