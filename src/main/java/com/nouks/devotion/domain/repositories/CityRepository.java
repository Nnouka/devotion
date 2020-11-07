package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.demographs.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
