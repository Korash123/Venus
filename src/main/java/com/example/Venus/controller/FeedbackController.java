package com.example.Venus.controller;


import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiRequest;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.FeedbackRequestDto;
import com.example.Venus.dto.response.FeedBackResponseDTO;
import com.example.Venus.dto.response.RatingSummaryResponseDTO;
import com.example.Venus.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(URL_CONSTANTS.Feedback.USER_BASE_URL)
@RequiredArgsConstructor
public class FeedbackController extends BaseController {
    private final FeedbackService feedbackService;

    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> saveFeedback(@RequestBody GlobalApiRequest<FeedbackRequestDto> request) {
        feedbackService.createFeedback(request.getData());
        return getSuccessResponse("feedback.create", HttpStatus.OK);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_BY_ID)
    public GlobalApiResponse<?> getFeedbackById() {
        FeedBackResponseDTO responseDTO = feedbackService.getFeedback();
        return getSuccessResponse("feedback.get", responseDTO,HttpStatus.OK);
    }
    @GetMapping(URL_CONSTANTS.COMMON.SUMMARY)
    public GlobalApiResponse<?> getFeedbackSummary() {
        RatingSummaryResponseDTO summaryResponseDTO = feedbackService.getRatingSummary();
        return getSuccessResponse("feedback.get", summaryResponseDTO,HttpStatus.OK);
    }

}
