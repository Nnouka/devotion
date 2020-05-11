package com.nouks.devotion.security.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {
  @Bean
  public RestTemplate restTemplate() {
    // added  HttpComponentsClientHttpRequestFactory because without it we couldn't getResponseBodyAsString()
    int timeout = 240000; // 4 mins
    CloseableHttpClient httpClient = HttpClients.custom()
      .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(httpClient);
    httpRequestFactory.setConnectTimeout(timeout);
    return new RestTemplate(httpRequestFactory);
  }
}
