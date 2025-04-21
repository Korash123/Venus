//package com.example.Venus.entities;
//
//
//import com.example.Venus.base.BaseEntity;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//@Entity
//@Table(name = "course_content",schema = "course_contents")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class CourseContent extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, name = "user_id")
//    private Long userID;
//    @Column(name = "courses_id")
//    private Long courseId;
//    @Column(name = "title")
//    private String title;
//    @Column(name = "description")
//    private String description;
//    @Column(name = "duration")
//    private Long duration;
//
//}