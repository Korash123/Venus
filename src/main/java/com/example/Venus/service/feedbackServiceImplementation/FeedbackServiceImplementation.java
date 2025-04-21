package com.example.Venus.service.feedbackServiceImplementation;

import com.example.Venus.dto.request.FeedbackRequestDto;
import com.example.Venus.dto.response.FeedBackResponseDTO;
import com.example.Venus.dto.response.RatingSummaryResponseDTO;
import com.example.Venus.entities.FeedBack;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.repo.FeedbackRepo;
import com.example.Venus.service.FeedbackService;
import com.example.Venus.utils.LoggedInUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class FeedbackServiceImplementation implements FeedbackService {
    private final FeedbackRepo feedbackRepo;
    private final LoggedInUserUtil loggedInUserUtil;


    @Override
    public void createFeedback(FeedbackRequestDto feedbackRequestDto) {
        Long userId = loggedInUserUtil.getLoggedInUserId();

        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(userId);
        feedBack.setMessage(feedbackRequestDto.getMessage());
        feedBack.setRating(feedbackRequestDto.getRating());
        feedbackRepo.save(feedBack);
    }

    @Override
    public FeedBackResponseDTO getFeedback() {
        Long userId = loggedInUserUtil.getLoggedInUserId();
        FeedBack feedBack = feedbackRepo.findByUserId(userId).orElseThrow(()->
                new ResourceNotFoundException("Feedback Not Found"));
        if (Boolean.FALSE.equals(feedBack.getIsDeleted())) {
            throw new ResourceNotFoundException("Feedback Not Found");
        }

        FeedBackResponseDTO feedBackResponseDTO = new FeedBackResponseDTO();
        feedBackResponseDTO.setMessage(feedBack.getMessage());
        feedBackResponseDTO.setRating(feedBack.getRating());

        return feedBackResponseDTO;
    }

    @Override
    public RatingSummaryResponseDTO getRatingSummary() {

        List<FeedBack> feedbacks = feedbackRepo.findAll();

        double totalRating = 0;
        int count = 0;

        Map<Integer, Long> ratingsCount = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            ratingsCount.put(i, 0L);
        }

        for (FeedBack fb : feedbacks) {
            int rating = fb.getRating().intValue();
            totalRating += rating;
            count++;

            ratingsCount.put(rating, ratingsCount.getOrDefault(rating, 0L) + 1);
        }

        double avg = count > 0 ? totalRating / count : 0;

        RatingSummaryResponseDTO response = new RatingSummaryResponseDTO();
        response.setAverageRating(avg);
        response.setRatingsCount(ratingsCount);

        return response;
    }


}
