package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.Congregation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongregationRepository extends JpaRepository<Congregation, Long> {
}
