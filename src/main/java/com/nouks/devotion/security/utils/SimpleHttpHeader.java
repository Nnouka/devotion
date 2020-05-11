package com.nouks.devotion.security.utils;

public class SimpleHttpHeader {
  String key;
  String value;

  public SimpleHttpHeader() {
  }

  public SimpleHttpHeader(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
