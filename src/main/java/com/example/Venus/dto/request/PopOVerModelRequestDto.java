package com.example.Venus.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PopOVerModelRequestDto {

    MultipartFile file;
}
