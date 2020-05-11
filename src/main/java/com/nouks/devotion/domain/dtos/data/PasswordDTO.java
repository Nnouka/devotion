package com.nouks.devotion.domain.dtos.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordDTO {
  @NotBlank(message = "password is required")
  private String password;
}
