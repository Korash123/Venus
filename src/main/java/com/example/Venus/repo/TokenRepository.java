package com.example.Venus.repo;


import com.example.Venus.entities.AuthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author korash waiba
 * @version v1.0
 * @since 12/13/2024
 **/
public interface TokenRepository extends JpaRepository<AuthLog, Long> {
    @Query("SELECT u FROM AuthLog u WHERE u.refreshToken = :refreshToken")
    Optional<AuthLog> findByRefreshToken(String refreshToken);

    Optional<AuthLog> findByEmail(String email);
    Optional<AuthLog> findByAccessToken(String token);
    Optional<AuthLog> findByEmailAndAccessToken(String email, String accessToken);
    void deleteByEmailAndAccessToken(String email, String accessToken);
}
