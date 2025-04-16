package com.example.Venus.controller;


import com.example.Venus.base.BaseController;
import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.dto.global.GlobalApiRequest;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.FacultyStaffRequestDto;
import com.example.Venus.dto.response.FacultyStaffResponseDto;
import com.example.Venus.service.FacultyStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANTS.FacultyStaffs.USER_BASE_URL)
public class FacultyStaffController extends BaseController {

    private final FacultyStaffService facultyStaffService;

    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> createAndUpdateFacultyStaff(@ModelAttribute FacultyStaffRequestDto facultyStaffRequestDto) throws IOException {
        facultyStaffService.createAndUpdateFacultyStaff(facultyStaffRequestDto);
        return getSuccessResponse("faculty.staff.create", HttpStatus.OK);
    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_ALL)
    public GlobalApiResponse<?> getAllFacultyStaff() throws Exception {
        List<FacultyStaffResponseDto> responseDtos = facultyStaffService.getAllFacultyStaff();
        return getSuccessResponse("faculty.staff.all", responseDtos, HttpStatus.OK);

    }

    @GetMapping(URL_CONSTANTS.COMMON.GET_BY_ID)
    public GlobalApiResponse<?> getFacultyStaffById() throws Exception {
       FacultyStaffResponseDto responseDtos = facultyStaffService.getFacultyStaffById();
        return getSuccessResponse("faculty.staff", responseDtos, HttpStatus.OK);
    }

    @DeleteMapping(URL_CONSTANTS.COMMON.DELETE_BY_ID)
    public GlobalApiResponse<?> deleteDocument(@RequestBody GlobalApiRequest<Long> request) {
        facultyStaffService.deleteFacultyStaffById(request.getData());
        return getSuccessResponse("faculty.delete.success", "faculty deleted successfully", HttpStatus.NO_CONTENT);
    }

}
