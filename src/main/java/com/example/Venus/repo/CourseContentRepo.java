//package com.example.Venus.repo;
//
//import com.example.Venus.entities.CourseContent;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface CourseContentRepo extends JpaRepository<CourseContent, Long> {
//
//    Optional<CourseContent> findByIdAndIsDeletedFalse(Long id);
//    List<CourseContent> findByIsDeletedFalse();
//    Optional<CourseContent> findByTitleAndCourseId(String title, Long courseId);
//
//}
