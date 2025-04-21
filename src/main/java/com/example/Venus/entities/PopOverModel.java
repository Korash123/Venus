package com.example.Venus.entities;


import com.example.Venus.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "popovermodel", schema = "popover_model")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PopOverModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String imageUrl;

}
