package com.example.Venus.entities;


import com.example.Venus.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events_news", schema = "event_news")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventNew extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(name = "title" , nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "published_at")
    private LocalDate publishedAt;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "document_url")
    private String imageUrl;

}
