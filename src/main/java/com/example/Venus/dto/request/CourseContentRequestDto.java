package com.example.Venus.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentRequestDto {

    private Long courseId;
    private String title;
    private String description;
    private Long duration;
}
