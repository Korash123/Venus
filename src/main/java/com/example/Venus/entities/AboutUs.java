package com.example.Venus.entities;

import com.example.Venus.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "about_us", schema = "about_us")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AboutUs extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
}
