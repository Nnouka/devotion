package com.nouks.devotion.domain.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
  @NotBlank(message = "username is required")
  private String username;
  @NotBlank(message = "password is required")
  private String password;
}
