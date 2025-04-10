package com.example.Venus.entities;


import com.example.Venus.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author korash waiba
 * @version v1.0
 * @since 12/13/2024
 */
@Entity
@Table(name = "auth_log", schema = "auth",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "accessToken","refreshToken"})
        })
@Getter
@Setter
public class AuthLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_log_seq")
    @SequenceGenerator(name = "auth_log_seq", sequenceName = "auth.auth_log_seq", allocationSize = 1)
    private Long id;

    //@Column(nullable = false, length = 255, unique = true)
    private String email;

    //@Column(nullable = false, length = 512, unique = true)
    private String accessToken;

    //@Column(nullable = false, length = 512, unique = true)
    private String refreshToken;

    //@Column(nullable = false)
    private LocalDateTime accessTokenIssuedAt;

    //@Column(nullable = false)
    private LocalDateTime refreshTokenIssuedAt;

    //@Column(nullable = false)
    private LocalDateTime accessTokenExpiresAt;

    //@Column(nullable = false)
    private LocalDateTime refreshTokenExpiresAt;

    private Boolean isLogout;
}
