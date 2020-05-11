package com.nouks.devotion.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.nouks.devotion.exceptions.ErrorCodes.*;


/**
 * @author nouks
 *
 * @date 18 Oct 2019
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                         HttpServletRequest request){
        logger.warn("Handling Error: MethodArgumentNotValidException, {}", VALIDATION_ERROR.toString());
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setUri(request.getRequestURI());
        validationErrorResponse.setCode(VALIDATION_ERROR.toString());
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();
        StringBuilder message = new StringBuilder("MethodArgumentNotValidException:");

        for(FieldError fieldError: fieldErrorList){
            validationErrorResponse.getErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
            message.append(" #").append(fieldError.getField());
        }
        message.append(" @errors.");
        validationErrorResponse.setMessage(message.toString());

        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                               HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(INVALID_FORMAT.toString());
        String dev_message = ex.getMessage();
        String message = null;
        if (dev_message != null ){
            if(dev_message.contains("expected format") && dev_message.contains(";")){
                message = dev_message.substring(dev_message.indexOf("expected format"), dev_message.indexOf(';'));
            }else {
                message = dev_message;
            }

        }
        errorResponse.setMessage(message);
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setDevHint("validate form input data");
        logger.warn("Handling Error: HttpMessageNotReadableException, {}, {}", INVALID_FORMAT.toString(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ErrorResponse> handleMgException(ApiException ex, HttpServletRequest request) {
        logger.warn("Handling Error: {}, {}", ex.getErrorCode(), ex.getHttpStatus());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ex.getErrorCode());
        errorResponse.setDevHint(ex.getDevHint());
        errorResponse.setUri(request.getRequestURI());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}
