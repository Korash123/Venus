package com.example.Venus.repo;

import com.example.Venus.entities.PopOverModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PopOverModelRepo extends JpaRepository<PopOverModel, Long> {
    Long id(Long id);
}
