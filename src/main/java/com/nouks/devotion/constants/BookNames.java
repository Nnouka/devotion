package com.nouks.devotion.constants;

public enum BookNames {
  ENROLLMENT("Enrollment into a program");
  private String message;
  BookNames(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
