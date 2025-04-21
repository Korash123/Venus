package com.example.Venus.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BlogRequestDto {

    private String title;
    private String content;
    private MultipartFile imageUrl;

}