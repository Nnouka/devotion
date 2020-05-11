package com.nouks.devotion.controllers;

import com.nouks.devotion.domain.dtos.UserDTO;
import com.nouks.devotion.domain.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected/user")
public class UserResourceController {
  private UserService userService;

  @Autowired
  public UserResourceController(UserService userService) {
    this.userService = userService;
  }
  @GetMapping("/details")
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<UserDTO> user() {
    return ResponseEntity.ok(userService.getUserInfo());
  }
}
