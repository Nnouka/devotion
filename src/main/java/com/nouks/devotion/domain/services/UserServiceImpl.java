package com.nouks.devotion.domain.services;

import com.nouks.devotion.constants.ApiConstants;
import com.nouks.devotion.domain.dtos.UserDTO;
import com.nouks.devotion.domain.dtos.data.EmailAddress;
import com.nouks.devotion.domain.dtos.data.MailCtxDto;
import com.nouks.devotion.domain.dtos.data.PasswordDTO;
import com.nouks.devotion.domain.dtos.data.SimpleMailDTO;
import com.nouks.devotion.domain.dtos.requests.UserRegistrationDto;
import com.nouks.devotion.domain.models.Role;
import com.nouks.devotion.domain.models.User;
import com.nouks.devotion.domain.repositories.RoleRepository;
import com.nouks.devotion.domain.repositories.UserRepository;
import com.nouks.devotion.domain.services.interfaces.AuthFacade;
import com.nouks.devotion.domain.services.interfaces.UserService;
import com.nouks.devotion.exceptions.ApiException;
import com.nouks.devotion.exceptions.ErrorCodes;
import com.nouks.devotion.mails.MailClient;
import com.nouks.devotion.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private AuthFacade authFacade;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private MailClient mailClient;
  private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setAuthFacade(AuthFacade authFacade) {
    this.authFacade = authFacade;
  }

  @Autowired
  public void setRoleRepository(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  public void setMailClient(MailClient mailClient) {
    this.mailClient = mailClient;
  }


  @Override
  public UserDTO getUserInfo() {
    return new UserDTO(getCurrentAuthUser());
  }

  @Override
  public User getCurrentAuthUser() {
    Optional<User> optionalUser;
    try {
      optionalUser = userRepository.findFirstByEmail(authFacade.getAuthentication().getName());
    } catch (Exception ex) {
      throw new ApiException(ErrorCodes.BAD_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED, ErrorCodes.BAD_CREDENTIALS.toString(), "");
    }
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    throw new ApiException(ErrorCodes.BAD_CREDENTIALS.getMessage(), HttpStatus.NOT_FOUND, ErrorCodes.BAD_CREDENTIALS.toString(), "");
  }

  @Override
  public void registerUser(UserRegistrationDto userRegistrationDto) {
    validateUserEmail(userRegistrationDto.getEmail());
    validateUserPhone(userRegistrationDto.getPhone());
    User user = new User();
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    user.setFullName(userRegistrationDto.getFullName());
    user.setEmail(userRegistrationDto.getEmail());
    user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
    user.setPhone(userRegistrationDto.getPhone());
    user.setEnabled(true);
    user.setTokenExpired(false);
    user.setRoles(Collections.singletonList(getOrSave("USER")));
    userRepository.save(user);
    logger.info("registered: {}", user.getFullName());
    // send activation mail
    sendActivationEmail(user);
  }

  @Override
  public void verifyAccount(String email) {
    logger.info("Verifying email: {}", email);
    Optional<User> optionalUser = userRepository.findFirstByEmail(email);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.getEmailVerifiedAt() == null) {
        user.setEmailVerifiedAt(LocalDateTime.now());
        logger.info("Welcoming: {}", user.getFullName());
        userRepository.save(user);
        // send welcome email
        sendWelcomeEmail(user);
      }
    } else {
      throw new ApiException(ErrorCodes.ACCOUNT_VERIFICATION_FAILED.getMessage(),
        HttpStatus.CONFLICT, ErrorCodes.ACCOUNT_VERIFICATION_FAILED.toString(), "");
    }
  }

  @Override
  public List<User> getAdmins() {
    return userRepository.findAll().stream().filter(
      user -> rolesContainsAdmin(user.getRoles())
    ).collect(Collectors.toList());
  }

  @Override
  public void forgotPassword(SimpleMailDTO simpleMailDTO) {
    User user = verifyAndGetOrElseRejectEmail(simpleMailDTO.getEmail());
    user.setPasswordResetCode(generateResetCode());
    sendPasswordResetMail(userRepository.save(user));
  }

  @Override
  public void resetPassword(String code, PasswordDTO dto) {
    User user = getUserByResetCode(code);
    user.setPasswordResetCode(null);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    userRepository.save(user);
  }

  private boolean rolesContainsAdmin(List<Role> roles) {
    for (Role role: roles) {
      if (role != null && role.getName().equals("ADMIN")) return true;
    }
    return false;
  }
  private void validateUserEmail(String email) {
    if (userRepository.findFirstByEmail(email).isPresent()) {
      throw new ApiException(ErrorCodes.EMAIL_ALREADY_IN_USE.getMessage(), HttpStatus.CONFLICT, ErrorCodes.EMAIL_ALREADY_IN_USE.toString(), "");
    }
  }

  private void validateUserPhone(String phone) {
    if (userRepository.findFirstByPhone(phone).isPresent()) {
      throw new ApiException(ErrorCodes.PHONE_NUMBER_ALREADY_IN_USE.getMessage(), HttpStatus.CONFLICT, ErrorCodes.PHONE_NUMBER_ALREADY_IN_USE.toString(), "");
    }
  }
  private Role getOrSave(String name) {
    Optional<Role> roleOptional = roleRepository.findFirstByName(name);
    if (roleOptional.isPresent()) {
      return roleOptional.get();
    } else {
      Role role = new Role();
      role.setName("USER");
      return roleRepository.save(role);
    }
  }
  private void sendActivationEmail(User user) {
    EmailAddress emailAddress = new EmailAddress(user.getFullName(), user.getEmail());
    List<MailCtxDto> mailCtxDtos = new ArrayList<>(Arrays.asList(
      new MailCtxDto("appName", ApiConstants.APP_NAME),
      new MailCtxDto("subscriptionDate", new Date()),
      new MailCtxDto("logoUrl", getLogoUrl()),
      new MailCtxDto("activationUrl", getActivationUrl(user.getEmail())))
    );
    Thread thread = new Thread(() -> mailClient.sendActivationMail(emailAddress,
      ApiConstants.ACCOUNT_ACTIVATION_FROM_ADDRESS, ApiConstants.ACCOUNT_ACTIVATION_SUBJECT, Locale.ENGLISH, mailCtxDtos));
    thread.start();
  }

  private void sendWelcomeEmail(User user) {
    EmailAddress emailAddress = new EmailAddress(user.getFullName(), user.getEmail());
    List<MailCtxDto> mailCtxDtos = new ArrayList<>(Arrays.asList(
      new MailCtxDto("appName", ApiConstants.APP_NAME),
      new MailCtxDto("services", "Services"),
      new MailCtxDto("logoUrl", getLogoUrl()),
      new MailCtxDto("baseUrl", HttpUtils.getBaseUrl()))
    );
    Thread thread = new Thread(() -> mailClient.sendWelcomeMail(emailAddress,
      ApiConstants.INFO_FROM_ADDRESS, ApiConstants.GET_STARTED_SUBJECT, Locale.ENGLISH, mailCtxDtos));
    thread.start();
  }

  private void sendPasswordResetMail(User user) {
    EmailAddress emailAddress = new EmailAddress(user.getFullName(), user.getEmail());
    List<MailCtxDto> mailCtxDtos = new ArrayList<>(Arrays.asList(
      new MailCtxDto("appName", ApiConstants.APP_NAME),
      new MailCtxDto("requestDate", new Date()),
      new MailCtxDto("logoUrl", getLogoUrl()),
      new MailCtxDto("resetUrl", getPasswordResetUrl(user.getPasswordResetCode())))
    );
    Thread thread = new Thread(() -> mailClient.sendResetPasswordMail(emailAddress,
      ApiConstants.NO_REPLY_FROM_ADDRESS, ApiConstants.PASSWORD_RESET_SUBJECT, Locale.ENGLISH, mailCtxDtos));
    thread.start();
  }

  private String getLogoUrl() {
    return HttpUtils.getLogoUrl();
  }

  private String getActivationUrl(String email) {
    return HttpUtils.getBaseUrl() + "/api/public/user/verify?ref=" + email;
  }
  private String getPasswordResetUrl(String code) {
    return HttpUtils.getBaseUrl() + "/api/public/user/password/init?ref=" + code;
  }
  private User verifyAndGetOrElseRejectEmail(String email) {
    Optional<User> optional = userRepository.findFirstByEmail(email);
    if (!optional.isPresent()) {
      throw new ApiException(ErrorCodes.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND, ErrorCodes.RESOURCE_NOT_FOUND.toString());
    } else {
      User user = optional.get();
      if (user.getEmailVerifiedAt() == null) {
        // user should verify email first before trying to change password
        throw new ApiException(ErrorCodes.EMAIL_NOT_VERIFIED.getMessage(), HttpStatus.FORBIDDEN, ErrorCodes.EMAIL_NOT_VERIFIED.toString());
      }
      return user;
    }
  }
  private String generateResetCode() {
    return UUID.randomUUID().toString().toLowerCase().replace("-", "");
  }

  private User getUserByResetCode(String code) {
    Optional<User> optional = userRepository.findFirstByPasswordResetCode(code);
    if (!optional.isPresent()) {
      throw new ApiException(ErrorCodes.RESOURCE_NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND, ErrorCodes.RESOURCE_NOT_FOUND.toString());
    } else {
      User user = optional.get();
      if (user.getEmailVerifiedAt() == null) {
        // user should verify email first before trying to change password
        throw new ApiException(ErrorCodes.EMAIL_NOT_VERIFIED.getMessage(), HttpStatus.FORBIDDEN, ErrorCodes.EMAIL_NOT_VERIFIED.toString());
      }
      return user;
    }
  }

}
