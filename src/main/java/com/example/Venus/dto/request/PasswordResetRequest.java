package com.example.Venus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {

    private String encryptedData;
    private String newPassword;
}