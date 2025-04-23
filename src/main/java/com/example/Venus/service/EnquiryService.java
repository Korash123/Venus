package com.example.Venus.service;

import com.example.Venus.dto.request.EnquiryRequestDto;
import com.example.Venus.dto.response.EnquiryResponseDto;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface EnquiryService {
    void enquiry(EnquiryRequestDto enquiryRequestDto) throws MessagingException, UnsupportedEncodingException;
    List<EnquiryResponseDto> getAllEnquiry();
}
