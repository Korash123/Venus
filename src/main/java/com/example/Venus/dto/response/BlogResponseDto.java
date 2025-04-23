package com.example.Venus.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class BlogResponseDto {
    private long id;
    private String title;
    private String content;
    private String imageUrl;
}
