package com.nouks.devotion.domain.dtos;

import com.nouks.devotion.domain.models.Privilege;
import com.nouks.devotion.domain.models.Role;
import com.nouks.devotion.domain.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {
  private Long id;
  private String fullName;
  private String email;
  private List<String> roles;
  private List<String> privileges;

  public UserDTO(User user) {
    if (user != null) {
      this.id = user.getId();
      this.fullName = user.getFullName();
      this.email = user.getEmail();
      this.roles = user.getRoles().stream().map(
        Role::getName
      ).collect(Collectors.toList());
      List<Privilege> privilegeList = new ArrayList<>();
      for (Role role: user.getRoles()) {
        privilegeList.addAll(role.getPrivileges());
      }
      this.privileges = privilegeList.stream().distinct().map(
        Privilege::getName
      ).collect(Collectors.toList());
    }
  }
}
