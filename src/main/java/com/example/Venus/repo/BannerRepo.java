package com.example.Venus.repo;

import com.example.Venus.entities.Banners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerRepo extends JpaRepository<Banners, Long> {
    Optional<Banners> findByUserIdAndTitle(Long userId, String title);
}
