package com.example.Venus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author korash waiba
 * @version v1.0
 * @since 12/13/2024
 **/
@Getter
@Setter
@AllArgsConstructor
public class AuthLogResponse {
    private String accessToken;
    private String refreshToken;
    private String fullName;
    private String email;
}