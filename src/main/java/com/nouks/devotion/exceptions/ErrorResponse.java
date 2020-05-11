package com.nouks.devotion.exceptions;

import lombok.Data;

/**
 * @author nouks
 *
 * @date 18 Oct 2019
 */
@Data
public class ErrorResponse {
    private String code;
    private String devHint;
    private String message;
    private String uri;
}
