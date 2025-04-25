package com.example.Venus.service;

import com.example.Venus.dto.request.BannerRequestDto;
import com.example.Venus.dto.response.BannersResponseDto;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    void createBanner(BannerRequestDto requestDto) throws IOException;
    List<BannersResponseDto> getAllBanner() throws Exception;
    void deleteBanner(Long id);
}
