package com.nouks.devotion.security.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt.resource")
@Data
public class ResourceServerProps {
  private String id;
}
