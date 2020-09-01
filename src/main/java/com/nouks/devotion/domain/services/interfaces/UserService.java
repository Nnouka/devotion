package com.nouks.devotion.domain.services.interfaces;


import com.nouks.devotion.domain.dtos.UserDTO;
import com.nouks.devotion.domain.dtos.data.PasswordDTO;
import com.nouks.devotion.domain.dtos.data.SimpleMailDTO;
import com.nouks.devotion.domain.dtos.requests.UserRegistrationDto;
import com.nouks.devotion.domain.models.User;

import java.util.List;

public interface UserService {
  UserDTO getUserInfo();
  User getCurrentAuthUser();
  void registerUser(UserRegistrationDto userRegistrationDto);
  void verifyAccount(String email);
  List<User> getAdmins();
  void forgotPassword(SimpleMailDTO simpleMailDTO);
  void resetPassword(String code, PasswordDTO dto);
  Boolean userExists(String externalId);
}
