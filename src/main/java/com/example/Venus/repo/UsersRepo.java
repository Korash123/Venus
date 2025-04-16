package com.example.Venus.repo;

import com.example.Venus.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/*
    @created 04/07/2025 11:08 AM
    @project users
    @author korash.waiba
*/

public interface UsersRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
//    List<Users> findByIsDeletedFalse();
    Optional<Users> findByIdAndIsDeletedFalse(Long id);
    Optional<Users> findByVbmId(String vbmId);
//    Optional<Users> findByRclIdAndIsDeletedFalse(String rclId);
//    List<Users> findByDepartmentId(Long departmentId);

}
