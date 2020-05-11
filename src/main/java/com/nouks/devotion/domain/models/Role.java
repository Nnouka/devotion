package com.nouks.devotion.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToMany(mappedBy = "roles")
  private List<User> users;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  @ManyToMany
  @JoinTable(
    name = "role_privilege",
    joinColumns = @JoinColumn(
      name = "role_id", referencedColumnName = "id"
    ), inverseJoinColumns = @JoinColumn(
      name = "privilege_id", referencedColumnName = "id"
  ))
  private List<Privilege> privileges;
}
