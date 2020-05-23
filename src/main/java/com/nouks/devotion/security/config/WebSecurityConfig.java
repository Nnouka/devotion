package com.nouks.devotion.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private PasswordEncoder passwordEncoder;
  private UserDetailsService userDetailsService;

  @Autowired
  @Qualifier("customUserDetailsService")
  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/", "/#/**", "/favicon.ico", "/*.js", "/*.css", "/*.html", "/assets/**", "/*.woff?", "/*.ttf", "/*.js.*", "/*.*g")
    .antMatchers("/v2/api-docs")
    .antMatchers("/swagger-resources/**")//
      .antMatchers("/swagger-ui.html")//
      .antMatchers("/configuration/**")//
      .antMatchers("/webjars/**")//
      .antMatchers("/public")
      .antMatchers("/h2-console/**/**")
      .antMatchers("/api/public/**");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .anonymous().disable()
      .authorizeRequests()
      .antMatchers("/index.html", "/", "/#/", "/api/public/**").permitAll()
      .antMatchers("/", "/#/**", "/favicon.ico", "/*.js", "/*.css", "/*.html", "/assets/**", "/*.woff?", "/*.ttf", "/*.js.*", "/*.jpeg", "/*.*g").permitAll()
      .antMatchers("/api/public/**").permitAll()
      .anyRequest().authenticated();
      /*.and()
      .logout()
      .logoutSuccessUrl("/api/public/user/logout/success")
      .clearAuthentication(true)
      .invalidateHttpSession(true)
      .deleteCookies("JSESSIONID")
      .permitAll()
    .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());*/
      http.exceptionHandling().accessDeniedPage("/");
//    http.addFilter(corsFilter());
  }



  @Bean
  public PasswordEncoder passwordEncoder() {
    if (passwordEncoder == null) {
      passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    return passwordEncoder;
  }
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
      }
    };
  }

  /*@Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("DELETE");

    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);

  }*/
}
