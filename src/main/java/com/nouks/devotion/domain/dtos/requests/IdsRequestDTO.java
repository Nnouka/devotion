package com.nouks.devotion.domain.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class IdsRequestDTO {
  @NotNull(message = "ids are required")
  @NotEmpty(message = "ids are required")
  private List<Long> ids;
}
