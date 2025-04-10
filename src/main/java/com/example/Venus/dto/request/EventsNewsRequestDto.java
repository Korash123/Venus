package com.example.Venus.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventsNewsRequestDto {
    private String title;
    private String description;
    private String location;
    private LocalDate publishedAt;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer imageIndex;
}
