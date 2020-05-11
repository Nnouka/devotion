package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findFirstByEmail(String email);
  Optional<User> findFirstByPhone(String phone);
  Optional<User> findFirstByPasswordResetCode(String code);
}
