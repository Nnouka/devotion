package com.nouks.devotion.domain.repositories;

import com.nouks.devotion.domain.models.demographs.LocationAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationAddressRepository extends JpaRepository<LocationAddress, Long> {
}
