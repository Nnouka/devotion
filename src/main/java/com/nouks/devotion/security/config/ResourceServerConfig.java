package com.nouks.devotion.security.config;

import com.nouks.devotion.security.props.ResourceServerProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  private ResourceServerProps resourceServerProps;

  @Autowired
  public void setResourceServerProps(ResourceServerProps resourceServerProps) {
    this.resourceServerProps = resourceServerProps;
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(resourceServerProps.getId()).stateless(false);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.anonymous().disable()
      .authorizeRequests()
      .antMatchers("/api/public/**").permitAll()
      .antMatchers("*.bundle.*").permitAll()
      .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
  }
}
