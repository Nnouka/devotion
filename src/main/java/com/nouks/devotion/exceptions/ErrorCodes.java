package com.nouks.devotion.exceptions;
/**
 * @author nouks
 *
 * @date 18 Oct 2019
 */
public enum ErrorCodes {
  SERVICE_NOT_FOUND("Service not found"),
  BAD_CREDENTIALS("Bad user credentials"),
  INVALID_FORMAT("Invalid input format"),
  TOKEN_REFRESH_FAILED("Failed to refresh token"),
  EMAIL_ALREADY_IN_USE("Sorry email is already taken"),
  PHONE_NUMBER_ALREADY_IN_USE("Sorry phone number is already taken"),
  FILE_NOT_FOUND("The file you specified does not exist or has been moved"),
  ACCOUNT_VERIFICATION_FAILED("Account verification failed. Dont worry we will try to fix it"),
  NULL_CLASSIFIER_ERROR("No classifier has been set"),
  CLASSIFIER_NOT_FOUND("Classifier not found"),
  CLASSIFIER_ABSTRACT_NOT_FOUND("Classifier Abstract not found"),
  ENROLLMENT_NOT_FOUND("Enrollment not found"),
  ENROLLMENT_ALREADY_BOOKED("Enrollment is already booked"),
  DUPLICATE_ENROLLMENT_ERROR("Duplicate enrollment is not allowed"),
  CLASSIFIER_ABSTRACT_IN_USE("Classifier Abstract in use"),
  CLASSIFIER_IN_USE("Classifier in use"),
  FIELD_OF_STUDY_NOT_FOUND("Field of Study not found"),
  FIELD_OF_STUDY_IN_USE("Field of Study in use"),
  PROGRAM_NOT_FOUND("Program not found"),
  PROGRAM_IN_USE("Program in use"),
  SERVICE_IN_USE("Service in use"),
  INSTITUTION_NOT_FOUND("Institution not found"),
  UNCOMPLETED_TRANSACTION("Transaction is not completed"),
  UNAUTHORIZED_TRANSACTION ("Transaction is unauthorized"),
  PAYMENT_FAILURE("Payment failed"),
  TRANSACTION_NOT_FOUND("Transaction not found"),
  TRANSACTION_COMPLETED("Transaction already completed"),
  RESOURCE_NOT_FOUND("Resource not found"),
  RESOURCE_DELETED_OR_MOVED("Resource deleted or moved"),
  VOUCHER_MAKER_DETAIL_NOT_EXISTS("Voucher maker details does not exist"),
  VOUCHER_MAKER_DETAIL_EXISTS("Voucher maker details have been already set for this user"),
  VOUCHER_AMOUNT_NOT_ENOUGH("Payment declined: the paying amount is more than the voucher amount"),
  VOUCHER_FRAUD("You are trying to use a voucher that doesn\'t exist. " +
    "This indicates that a fraudulent activity has occurred."),
  EMAIL_NOT_VERIFIED("Email has not been verified"),
  VALIDATION_ERROR("Input validation failed");
  private String message;

  ErrorCodes(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
