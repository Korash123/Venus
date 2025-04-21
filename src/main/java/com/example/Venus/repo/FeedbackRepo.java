package com.example.Venus.repo;


import com.example.Venus.entities.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepo extends JpaRepository<FeedBack, Long> {

    Optional<FeedBack> findByUserId(Long userId);
}
