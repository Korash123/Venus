package com.example.Venus.entities;

import com.example.Venus.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback", schema = "feed_back")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FeedBack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "users_id")
    private Long userId;
    @Column(name = "message")
    private String message;
    @Column(name = "rating")
    @Min(1)
    @Max(5)
    private Long rating;

}