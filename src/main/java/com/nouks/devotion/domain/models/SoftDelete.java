package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class SoftDelete {
  private LocalDateTime deletedAt;
}
