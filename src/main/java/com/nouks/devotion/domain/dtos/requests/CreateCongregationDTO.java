package com.nouks.devotion.domain.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCongregationDTO {
    @NotBlank(message = "name is required")
    private String name;
}
