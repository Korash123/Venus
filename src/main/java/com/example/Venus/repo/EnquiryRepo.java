package com.example.Venus.repo;

import com.example.Venus.entities.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnquiryRepo extends JpaRepository<Enquiry, Long> {

}
