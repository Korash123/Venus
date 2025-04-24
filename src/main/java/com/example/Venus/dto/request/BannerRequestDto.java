package com.example.Venus.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter

public class BannerRequestDto {

    private String title;
    private String description;
    MultipartFile image;
}
