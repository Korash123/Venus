package com.example.Venus.service;

import com.example.Venus.dto.request.FeedbackRequestDto;
import com.example.Venus.dto.response.FeedBackResponseDTO;
import com.example.Venus.dto.response.RatingSummaryResponseDTO;

import java.util.List;

public interface FeedbackService {
    void createFeedback(FeedbackRequestDto feedbackRequestDto);
    FeedBackResponseDTO getFeedback();
    RatingSummaryResponseDTO getRatingSummary();

}
