package com.nouks.devotion.domain.services.interfaces;


import org.springframework.security.core.Authentication;

public interface AuthFacade {
  Authentication getAuthentication();
}
