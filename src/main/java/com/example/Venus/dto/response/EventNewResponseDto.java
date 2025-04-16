package com.example.Venus.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class EventNewResponseDto {
    private String title;
    private String description;
    private String location;
    private String imageUrl;
    private LocalTime startTime;
    private LocalTime endTime;
}
