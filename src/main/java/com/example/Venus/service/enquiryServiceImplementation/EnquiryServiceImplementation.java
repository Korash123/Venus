package com.example.Venus.service.enquiryServiceImplementation;


import com.example.Venus.dto.request.EnquiryRequestDto;
import com.example.Venus.dto.response.EnquiryResponseDto;
import com.example.Venus.entities.Enquiry;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.repo.EnquiryRepo;
import com.example.Venus.service.EnquiryService;
import com.example.Venus.utils.EmailUtils;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class EnquiryServiceImplementation implements EnquiryService {
    private final EnquiryRepo enquiryRepo;
    private final EmailUtils emailUtils;


    @Override
    public void enquiry(EnquiryRequestDto enquiryRequestDto) throws MessagingException, UnsupportedEncodingException {

        Enquiry newEnquiry = new Enquiry();
        newEnquiry.setName(enquiryRequestDto.getName());
        newEnquiry.setPhoneNumber(enquiryRequestDto.getPhoneNumber());
        newEnquiry.setEmail(enquiryRequestDto.getEmail());
        newEnquiry.setProgram(enquiryRequestDto.getProgram());

        enquiryRepo.save(newEnquiry);

        String enquiryMail = "Getting Enquiries";
        String adminMail = "korashlama@gmail.com";

        emailUtils.sendEnquiryNotificationEmail(
                adminMail,
                enquiryRequestDto.getName(),
                enquiryRequestDto.getPhoneNumber(),
                enquiryRequestDto.getEmail(),
                enquiryRequestDto.getProgram()
        );

    }

    @Override
    public List<EnquiryResponseDto> getAllEnquiry() {
        List<Enquiry> enquiryList = enquiryRepo.findAll();
        List<EnquiryResponseDto> enquiryResponseDtoList = new ArrayList<>();

        for (Enquiry enquiry : enquiryList) {
            EnquiryResponseDto dto = new EnquiryResponseDto();
            dto.setId(enquiry.getId());
            dto.setName(enquiry.getName());
            dto.setPhoneNumber(enquiry.getPhoneNumber());
            dto.setEmail(enquiry.getEmail());
            dto.setProgram(enquiry.getProgram());
            enquiryResponseDtoList.add(dto);
        }

        return enquiryResponseDtoList;
    }

    @Override
    public void deleteEnquiry(Long id) {
        Enquiry enquiry = enquiryRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Enquiry not Found"));
        enquiry.setIsDeleted(true);
        enquiryRepo.save(enquiry);
    }


}
