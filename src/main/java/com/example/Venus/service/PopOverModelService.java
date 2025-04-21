package com.example.Venus.service;

import com.example.Venus.dto.request.PopOVerModelRequestDto;
import com.example.Venus.dto.response.PopOverModelResponseDto;

import java.io.IOException;
import java.util.List;

public interface PopOverModelService {
    void create(PopOVerModelRequestDto requestDto) throws IOException;
    List<PopOverModelResponseDto> showPopOverModel() throws Exception;
    void deletePopOverImage(Long id);
}
