package com.example.Venus.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CoursesRequestDto {

    private String title;
    private String description;
    private Long price;
    private Long duration;
    private MultipartFile imagUrl;

}