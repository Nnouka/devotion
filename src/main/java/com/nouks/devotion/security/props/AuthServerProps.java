package com.nouks.devotion.security.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt.security")
@Data
public class AuthServerProps {
  private String clientId;
  private String clientSecret;
  private Integer accessTokenValiditySeconds;
  private Integer refreshTokenValiditySeconds;
  private String signingKey;
}
