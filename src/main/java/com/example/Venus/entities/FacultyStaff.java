package com.example.Venus.entities;


import com.example.Venus.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faculty_staff", schema ="faculty_staff" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FacultyStaff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(name = "tittle", nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "imageUrl",nullable = false)
    private String imageUrl;
}
