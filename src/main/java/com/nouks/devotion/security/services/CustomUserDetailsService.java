package com.nouks.devotion.security.services;

import com.nouks.devotion.domain.models.Privilege;
import com.nouks.devotion.domain.models.Role;
import com.nouks.devotion.domain.models.User;
import com.nouks.devotion.domain.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("customUserDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
  private UserRepository userRepository;
  private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findFirstByEmail(email);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      logger.info("Authenticated: {} - {}", user.getFullName(), user.getEmail());
      return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles())
      );
    } else {
      throw new UsernameNotFoundException("Email " + email + " not found");
    }
  }

  private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }
  private List<String> getPrivileges(List<Role> roles) {
    List<Privilege> privileges = new ArrayList<>();
    for (Role role: roles) {
      privileges.addAll(role.getPrivileges());
    }
    return privileges.stream().distinct().map(
      Privilege::getName
    ).collect(Collectors.toList());
  }
  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    return privileges.stream().map(
      SimpleGrantedAuthority::new
    ).collect(Collectors.toList());
  }
}
