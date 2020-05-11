package com.nouks.devotion.domain.repositories;


import com.nouks.devotion.domain.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
