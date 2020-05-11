package com.nouks.devotion.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "refreshToken is required")
    private String refreshToken;
}
