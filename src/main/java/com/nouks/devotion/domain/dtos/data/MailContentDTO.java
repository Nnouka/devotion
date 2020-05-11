package com.nouks.devotion.domain.dtos.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MailContentDTO {
    @NotBlank(message = "message is required")
    private String message;
}
