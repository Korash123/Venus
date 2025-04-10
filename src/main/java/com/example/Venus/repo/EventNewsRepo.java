package com.example.Venus.repo;

import com.example.Venus.entities.EventNew;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventNewsRepo extends JpaRepository<EventNew,Long> {

    Optional<EventNew> findByTitle(String title);

}
