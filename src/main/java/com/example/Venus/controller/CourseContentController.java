//package com.example.Venus.controller;
//
//
//import com.example.Venus.base.BaseController;
//import com.example.Venus.contants.URL_CONSTANTS;
//import com.example.Venus.dto.global.GlobalApiRequest;
//import com.example.Venus.dto.global.GlobalApiResponse;
//import com.example.Venus.dto.request.CourseContentRequestDto;
//import com.example.Venus.dto.response.CourseContentResponseDto;
//import com.example.Venus.service.CourseContentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping(URL_CONSTANTS.CourseContent.COURSE_BASE_URL)
//@RequiredArgsConstructor
//public class CourseContentController extends BaseController {
//
//    private final CourseContentService courseContentService;
//
//
//    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
//    public GlobalApiResponse<?> createCourses(@ModelAttribute GlobalApiRequest<CourseContentRequestDto> request) throws IOException {
//        CourseContentRequestDto courseContentRequestDto = request.getData();
//        courseContentService.createCourseContent(courseContentRequestDto);
//        return getSuccessResponse("course.create.success", "Course created or updated successfully", HttpStatus.OK);
//    }
//
//    @PutMapping(URL_CONSTANTS.COMMON.UPDATE)
//    public GlobalApiResponse<?> updateCourse(@PathVariable Long id, @RequestBody GlobalApiRequest<CourseContentRequestDto> request) throws IOException {
//        courseContentService.updateCourseContent(id,request.getData());
//        return getSuccessResponse("province.updated.success", "Course Updated Successfully", HttpStatus.OK);
//    }
//
//
//    @DeleteMapping(URL_CONSTANTS.COMMON.DELETE_BY_ID)
//    public GlobalApiResponse<?> deleteDocument(@RequestBody GlobalApiRequest<Long> request) throws IOException {
//        courseContentService.deleteCourseContent(request.getData());
//        return getSuccessResponse("course.delete.success", "Course deleted successfully", HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping(URL_CONSTANTS.COMMON.GET_ALL)
//    public GlobalApiResponse<?> getAllDocuments(GlobalApiRequest<?> request) throws Exception {
//        List<CourseContentResponseDto> responseDtos = courseContentService.getAllCourseContent();
//        return getSuccessResponse("course.get.all.success", responseDtos, HttpStatus.OK);
//    }
//
//    @GetMapping(URL_CONSTANTS.COMMON.GETBYID)
//    public GlobalApiResponse<?> getCoursesById(@RequestBody GlobalApiRequest<Long> request) throws Exception {
//        courseContentService.getCourseContent(request.getData());
//        return getSuccessResponse("course.get.success", "Course Get successfully", HttpStatus.NO_CONTENT);
//    }
//
//
//}
