//package com.example.Venus.service.courseContentServiceImplementation;
//
//
//import com.example.Venus.dto.request.CourseContentRequestDto;
//import com.example.Venus.entities.CourseContent;
//import com.example.Venus.repo.CourseContentRepo;
//import com.example.Venus.service.CourseContentService;
//import com.example.Venus.utils.LoggedInUserUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CourseContentServiceImplementation implements CourseContentService {
//
//
//    private final CourseContentRepo courseContentRepo;
//    private final final LoggedInUserUtil loggedInUserUtil;
//
//
//    @Override
//    public void createCourseContent(CourseContentRequestDto requestDto) {
//        Long userId = loggedInUserUtil.getLoggedInUserId();
//        Optional<CourseContent> existingRecord = courseContentRepo.findByTitleAndCourseId(
//                requestDto.getTitle(), requestDto.getCourseId());
//
//        CourseContent courseContent = existingRecord.orElse(new CourseContent());
//        courseContent.setUserID(userId);
//        courseContent.setCourseId(requestDto.getCourseId());
//        courseContent.setTitle(requestDto.getTitle());
//        courseContent.setDescription(requestDto.getDescription());
//        courseContent.setDuration(requestDto.getDuration());
//        courseContentRepo.save(courseContent);
//
//    }
//
////    @Override
////    public void updateCourseContent(Long id, CourseContentRequestDto requestDto) {
////        CourseContent exitingCourseContent = courseContentRepo.findByIdAndIsDeletedFalse(id).orElseThrow(()->
////                new CoursesNotFoundException(new String[]{"CourseContent"}));
////        exitingCourseContent.setTitle(requestDto.getTitle());
////        exitingCourseContent.setDescription(requestDto.getDescription());
////        exitingCourseContent.setDuration(requestDto.getDuration());
////        courseContentRepo.save(exitingCourseContent);
////
////    }
//
//    @Override
//    public void deleteCourseContent(Long id) {
//        CourseContent courseContent = courseContentRepo.findById(id).orElseThrow(()->
//                new CoursesNotFoundException(new String[]{"CourseContent"}));
//        courseContent .setIsDeleted(true);
//        courseContentRepo.save(courseContent);
//    }
//
//    @Override
//    public CourseContentResponseDto getCourseContent(Long id) {
//
//        CourseContent courseContent = courseContentRepo.findByIdAndIsDeletedFalse(id).orElseThrow(()->
//                new CoursesNotFoundException(new String[]{"CourseContent"}));
//
//        CourseContentResponseDto courseContentResponseDto = new CourseContentResponseDto();
//        courseContentResponseDto.setCourseId(courseContent.getCourseId());
//        courseContentResponseDto.setTitle(courseContent.getTitle());
//        courseContentResponseDto.setDescription(courseContent.getDescription());
//        courseContentResponseDto.setDuration(courseContent.getDuration());
//        return courseContentResponseDto;
//    }
//
//    @Override
//    public List<CourseContentResponseDto> getAllCourseContent() {
//        List<CourseContent> courseContentList = courseContentRepo.findByIsDeletedFalse();
//
//        List<CourseContentResponseDto> courseContentResponseDtoList = new ArrayList<>();
//
//        for (CourseContent courseContent : courseContentList) {
//
//            CourseContentResponseDto courseContentResponseDto = new CourseContentResponseDto();
//            courseContentResponseDto.setCourseId(courseContent.getCourseId());
//            courseContentResponseDto.setTitle(courseContent.getTitle());
//            courseContentResponseDto.setDescription(courseContent.getDescription());
//            courseContentResponseDto.setDuration(courseContent.getDuration());
//
//            courseContentResponseDtoList.add(courseContentResponseDto);
//        }
//        return courseContentResponseDtoList;
//    }
//
//
//}
