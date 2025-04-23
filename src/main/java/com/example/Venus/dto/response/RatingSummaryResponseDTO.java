package com.example.Venus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class RatingSummaryResponseDTO {
    private long id;
    private double averageRating;
    private Map<Integer, Long> ratingsCount;

}
