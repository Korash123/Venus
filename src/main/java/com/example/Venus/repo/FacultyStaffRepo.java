package com.example.Venus.repo;

import com.example.Venus.entities.FacultyStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacultyStaffRepo extends JpaRepository<FacultyStaff, Long> {

    Optional<FacultyStaff> findByUserIdAndTitleAndDescription(Long userId,String title,String description);
    List<FacultyStaff> findByIsDeletedFalse();
    Optional<FacultyStaff> findByUserId(Long userId);
}
