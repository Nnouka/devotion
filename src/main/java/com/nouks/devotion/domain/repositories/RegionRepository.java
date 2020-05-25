package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
