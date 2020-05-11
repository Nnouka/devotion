package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findFirstByName(String name);
}
