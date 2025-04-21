package com.example.Venus.repo;

import com.example.Venus.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepo extends JpaRepository<Blog, Long> {

    Optional<Blog> findByUserIdAndTitleAndContent(Long userId, String title, String description);
    Optional<Blog> findByUserId(Long userId);
}
