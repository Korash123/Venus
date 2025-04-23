package com.example.Venus.controller;

import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiRequest;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.EnquiryRequestDto;
import com.example.Venus.service.EnquiryService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping(URL_CONSTANTS.Enquiry.ENQUIRY_BASE_URL)
@AllArgsConstructor
public class EnquiryController extends BaseController {

    private final EnquiryService enquiryService;

    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> createOrUpdateEventNews(@RequestBody GlobalApiRequest<EnquiryRequestDto> request) throws MessagingException, UnsupportedEncodingException {
        enquiryService.enquiry(request.getData());
        return getSuccessResponse("enquiry.create.success", HttpStatus.OK);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_ALL)
    public GlobalApiResponse<?> getAllEvents() {
        return  getSuccessResponse("enquiry.fetch.all", enquiryService.getAllEnquiry(),HttpStatus.OK);
    }
}
