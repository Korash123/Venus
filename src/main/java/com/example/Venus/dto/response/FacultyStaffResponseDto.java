package com.example.Venus.dto.response;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FacultyStaffResponseDto {
    private long id;
    private String title;
    private String description;
    private String imageUrl;
}
