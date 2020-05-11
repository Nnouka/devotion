package com.nouks.devotion.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouks
 *
 * @date 18 Oct 2019
 */
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
