package com.example.Venus.entities;

import com.example.Venus.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "enquiry",schema = "enquiries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Enquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String program;

}
