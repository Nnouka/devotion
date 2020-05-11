package com.nouks.devotion.security.config;

import com.nouks.devotion.security.props.AuthServerProps;
import com.nouks.devotion.security.services.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private AuthServerProps authServerProps;
  static final String GRANT_TYPE_PASSWORD = "password";
  static final String AUTHORIZATION_CODE = "authorization_code";
  static final String REFRESH_TOKEN = "refresh_token";
  static final String IMPLICIT = "implicit";
  static final String SCOPE_READ = "read";
  static final String SCOPE_WRITE = "write";
  static final String TRUST = "trust";
  private AuthenticationManager authenticationManager;
  private JwtTokenEnhancer jwtTokenEnhancer;
  private UserDetailsService userDetailsService;

  @Autowired
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Autowired
  public void setAuthServerProps(AuthServerProps authServerProps) {
    this.authServerProps = authServerProps;
  }

  @Autowired
  public void setJwtTokenEnhancer(JwtTokenEnhancer jwtTokenEnhancer) {
    this.jwtTokenEnhancer = jwtTokenEnhancer;
  }

  @Autowired
  @Qualifier("customUserDetailsService")
  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(authServerProps.getSigningKey());
    return converter;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
      .inMemory()
      .withClient(authServerProps.getClientId())
      .secret("{noop}" + authServerProps.getClientSecret())
      .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
      .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
      .accessTokenValiditySeconds(authServerProps.getAccessTokenValiditySeconds())
      .refreshTokenValiditySeconds(authServerProps.getRefreshTokenValiditySeconds());
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, accessTokenConverter()));
    endpoints.tokenStore(tokenStore())
      .authenticationManager(authenticationManager)
      .tokenEnhancer(tokenEnhancerChain)
      .userDetailsService(userDetailsService)
      .accessTokenConverter(accessTokenConverter());
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }
}
