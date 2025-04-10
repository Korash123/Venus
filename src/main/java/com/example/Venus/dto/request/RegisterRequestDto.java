package com.example.Venus.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Korash Waiba
 * @version v1.0
 * @since 04/07/2025
 **/
@Getter
@Setter
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "VALID EMAIL IS REQUIRED")
    private String email;
    @NotBlank(message = " VALID PASSWORD IS REQUIRED")
    private String password;
    private String fullName;
    private String phone;
    private Long roleId;

}