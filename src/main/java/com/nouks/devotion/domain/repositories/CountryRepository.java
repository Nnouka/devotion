package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.demographs.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
