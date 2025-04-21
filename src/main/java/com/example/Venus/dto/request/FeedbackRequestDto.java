package com.example.Venus.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequestDto {

    private String message;
    private Long rating;
}
