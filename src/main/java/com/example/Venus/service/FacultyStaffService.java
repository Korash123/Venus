package com.example.Venus.service;

import com.example.Venus.dto.request.FacultyStaffRequestDto;
import com.example.Venus.dto.response.FacultyStaffResponseDto;

import java.io.IOException;
import java.util.List;

public interface FacultyStaffService {
    void createAndUpdateFacultyStaff(FacultyStaffRequestDto facultyStaffRequestDto) throws IOException;
    List<FacultyStaffResponseDto> getAllFacultyStaff() throws Exception;
    FacultyStaffResponseDto getFacultyStaffById() throws Exception;
    void deleteFacultyStaffById(Long id);

}
