package com.nouks.devotion.domain.dtos.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationDto {
  private String fullName;
  @NotBlank(message = "email is required")
  @Email(message = "please input a valid email")
  private String email;
  @NotBlank(message = "phone is required")
  private String phone;
  @NotBlank(message = "password is required")
  private String password;
}
