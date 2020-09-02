package com.nouks.devotion.controllers;

import com.nouks.devotion.domain.dtos.data.PasswordDTO;
import com.nouks.devotion.domain.dtos.data.SimpleMailDTO;
import com.nouks.devotion.domain.dtos.requests.LoginRequestDTO;
import com.nouks.devotion.domain.dtos.requests.UserRegistrationDto;
import com.nouks.devotion.domain.services.interfaces.UserService;
import com.nouks.devotion.security.SecurityRestClient;
import com.nouks.devotion.security.dto.RefreshTokenRequest;
import com.nouks.devotion.security.props.AuthServerProps;
import com.nouks.devotion.utils.HttpUtils;
import com.nouks.devotion.security.utils.OAuth2Token;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;

@RestController
@RequestMapping("/api/public/user")
//@CrossOrigin(origins = "*")
public class UserController {
  private UserService userService;
  private AuthServerProps authServerProps;
  private SecurityRestClient securityRestClient;
  @Value("${app.ui.base-uri}")
  private String uiBaseUrl;

  @Autowired
  public void setAuthServerProps(AuthServerProps authServerProps) {
    this.authServerProps = authServerProps;
  }

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setSecurityRestClient(SecurityRestClient securityRestClient) {
    this.securityRestClient = securityRestClient;
  }
  @GetMapping("/logout/success")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("logout successful");
  }

  @PostMapping("/login")
  public ResponseEntity<OAuth2Token> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("username", loginRequestDTO.getUsername());
    params.add("password", loginRequestDTO.getPassword());
    HttpUtils.setBaseUrl(request.getScheme(), request.getServerName(), request.getServerPort());
    HttpUtils.setLang(request.getHeader("lang"));
    return ResponseEntity.ok(securityRestClient.getAccessToken(params));
  }
  @PostMapping("/token/refresh")
  public ResponseEntity<OAuth2Token> refreshToken(@RequestBody @Valid RefreshTokenRequest tokenRequest, HttpServletRequest request) {
    HttpUtils.setBaseUrl(request.getScheme(), request.getServerName(), request.getServerPort());
    return ResponseEntity.ok(securityRestClient.getAccessTokenViaRefreshToken(tokenRequest));
  }
  @PostMapping("/register")
  public ResponseEntity registerUser(@RequestBody @Valid UserRegistrationDto dto, HttpServletRequest request) {
    HttpUtils.setBaseUrl(request.getScheme(), request.getServerName());
    HttpUtils.setLang(request.getHeader("lang"));
    userService.registerUser(dto);
    return ResponseEntity.noContent().build();
  }
  @GetMapping("/verify")
  public RedirectView verifyAccount(@RequestParam("ref") String email, HttpServletRequest request) {
    HttpUtils.setBaseUrl(request.getScheme(), request.getServerName());
    HttpUtils.setLang(request.getHeader("lang"));
    System.out.println(uiBaseUrl);
    userService.verifyAccount(email);
    String m = "reg_verified";
    return new RedirectView(uiBaseUrl + "/login?r=" + m);
  }

  @PostMapping("/password/forgot")
  public ResponseEntity initForgotPasswordRequest(@RequestBody @Valid SimpleMailDTO mailDTO, HttpServletRequest request) {
    HttpUtils.setBaseUrl(request.getScheme(), request.getServerName());
    userService.forgotPassword(mailDTO);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/password/init")
  public RedirectView initResetPasswordRequest(@RequestParam("ref") String code) {
    return new RedirectView("/#/public/password/reset?ref=" +
      Base64.getUrlEncoder().encodeToString(code.getBytes()));
  }

  @PostMapping("/password/reset")
  public ResponseEntity resetPassword(@RequestBody @Valid PasswordDTO dto,
                                      @RequestParam("ref") String code,
                                      HttpServletRequest request) {
    HttpUtils.setBaseUrl(request.getScheme(), request.getServerName());
    userService.resetPassword(code, dto);
    return ResponseEntity.noContent().build();
  }
  @GetMapping("/exists")
  public ResponseEntity<Boolean> userExists(@RequestParam("externalId") String externalId) {
    return ResponseEntity.ok(userService.userExists(externalId));
  }
  @PostMapping("/subscribe")
  public ResponseEntity<Boolean> subscribeUser(@RequestParam("email") String email) {
    return email.trim().contains("@") ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
  }
}
