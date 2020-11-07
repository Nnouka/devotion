package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.pivots.CongregationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CongregationUserRepository extends JpaRepository<CongregationUser, Long> {
    Optional<CongregationUser> findFirstByCongregation_IdAndMinister(Long id, Boolean isMinister);
}
