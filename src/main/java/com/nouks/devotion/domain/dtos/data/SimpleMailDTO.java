package com.nouks.devotion.domain.dtos.data;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SimpleMailDTO {
  @NotBlank(message = "email is require")
  @Email(message = "wrong email format")
  String email;
}
