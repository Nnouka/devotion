package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.pivots.CongregationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongregationUserRepository extends JpaRepository<CongregationUser, Long> {
}
