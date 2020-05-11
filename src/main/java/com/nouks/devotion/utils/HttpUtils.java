package com.nouks.devotion.utils;

import com.nouks.devotion.security.utils.SimpleHttpHeader;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;

public class HttpUtils {
  private static String baseUrl;
  public static HttpHeaders createAuthHeader(String username, String password) {
    return new HttpHeaders(){{
      String auth = username + ":" + password;
      byte[] encodeAuth = Base64.encodeBase64(
        auth.getBytes(StandardCharsets.US_ASCII)
      );
      String authHeader = "Basic " + new String(encodeAuth);
      set("Authorization", authHeader);
    }};
  }

  public static SimpleHttpHeader createBasicAuthHeader(String username, String password) {
    String auth = username + ":" + password;
    byte[] encodeAuth = Base64.encodeBase64(
      auth.getBytes(StandardCharsets.US_ASCII)
    );
    String authHeader = "Basic " + new String(encodeAuth);
    return new SimpleHttpHeader("Authorization", authHeader);
  }

  public static String getBaseUrl() {
    return baseUrl;
  }

  public static void setBaseUrl(String protocol, String serverName, Integer port) {
    HttpUtils.baseUrl =  port == null || port == 0 ? protocol + "://" + serverName : protocol + "://" + serverName + ":" + port;;
  }
  public static void setBaseUrl(String protocol, String serverName) {
    HttpUtils.baseUrl = protocol + "://" + serverName;
  }

  public static String getLogoUrl() {
    return baseUrl + "/api/public/resources/logos/logo-mini.png";
  }
}
