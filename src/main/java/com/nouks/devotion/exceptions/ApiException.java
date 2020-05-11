package com.nouks.devotion.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author nouks
 *
 * @date 18 Oct 2019
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorCode;
    private String devHint;

    public ApiException(String message, HttpStatus httpStatus, String errorCode, String devHint) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.devHint = devHint;
    }

  public ApiException(String message, HttpStatus httpStatus, String errorCode) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
    this.devHint = "no hints";
  }
}
