package com.nouks.devotion.security;


import com.nouks.devotion.exceptions.ApiException;
import com.nouks.devotion.exceptions.ErrorCodes;
import com.nouks.devotion.security.dto.RefreshTokenRequest;
import com.nouks.devotion.security.props.AuthServerProps;
import com.nouks.devotion.utils.HttpUtils;
import com.nouks.devotion.security.utils.OAuth2Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class SecurityRestClient {
  private RestTemplate restTemplate;
  private AuthServerProps authServerProps;
  private Logger logger = LoggerFactory.getLogger(SecurityRestClient.class);
  private static final String TOKEN_RESOURCE_PATH = "/oauth/token";

  @Autowired
  public SecurityRestClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Autowired
  public void setAuthServerProps(AuthServerProps authServerProps) {
    this.authServerProps = authServerProps;
  }

  public OAuth2Token getAccessToken(MultiValueMap<String, String> urlParameters){
    HttpHeaders headers = HttpUtils.createAuthHeader(authServerProps.getClientId(), authServerProps.getClientSecret());
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    urlParameters.add("grant_type", "password");
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(urlParameters, headers);
    try {
      ResponseEntity<OAuth2Token> entity = restTemplate.postForEntity(HttpUtils.getBaseUrl() + TOKEN_RESOURCE_PATH, request, OAuth2Token.class);
      logger.info("Status Code: {}", entity.getStatusCode().toString());
      return entity.getBody();
    } catch (Exception httpEx) {
      httpEx.printStackTrace();
     throw new ApiException(ErrorCodes.BAD_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED, ErrorCodes.BAD_CREDENTIALS.toString(), "");
    }
  }
  public OAuth2Token getAccessTokenViaRefreshToken(RefreshTokenRequest refreshTokenRequest){
    MultiValueMap<String, String> urlParameters = new LinkedMultiValueMap<>();
    urlParameters.add("refresh_token", refreshTokenRequest.getRefreshToken());
    urlParameters.add("grant_type", "refresh_token");
    urlParameters.add("client_id", authServerProps.getClientId());
    HttpHeaders headers = HttpUtils.createAuthHeader(authServerProps.getClientId(), authServerProps.getClientSecret());
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(urlParameters, headers);
    try {
      ResponseEntity<OAuth2Token> entity = restTemplate.postForEntity(HttpUtils.getBaseUrl() + TOKEN_RESOURCE_PATH, request, OAuth2Token.class);
      logger.info("Status Code: {}", entity.getStatusCode().toString());
      if (entity.getStatusCode() == HttpStatus.OK) {
        return entity.getBody();
      } else {
        throw new ApiException(ErrorCodes.TOKEN_REFRESH_FAILED.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, ErrorCodes.TOKEN_REFRESH_FAILED.toString(), "");
      }
    } catch (RestClientResponseException httpEx) {
      httpEx.printStackTrace();
      throw new ApiException(ErrorCodes.TOKEN_REFRESH_FAILED.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, ErrorCodes.TOKEN_REFRESH_FAILED.toString(), "");
    }
  }
}
