package com.nouks.devotion.domain.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLocationDto {
    @NotBlank(message = "address is required")
    private String address;
    @NotNull(message = "cityId is required")
    private Long cityId;
    private double latitude;
    private double longitude;
}
