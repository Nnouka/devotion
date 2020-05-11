package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "privileges")
public class Privilege {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String icon;
  private String uri;
  private String description;
  private Boolean collapsible;
  @Column(name = "collapse_to")
  private String collapseTo;
  private Boolean displayed;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  @ManyToMany(mappedBy = "privileges")
  private List<Role> roles;
}
