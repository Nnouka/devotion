package com.nouks.devotion.domain.services;


import com.nouks.devotion.domain.services.interfaces.DateValidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidatorWithDateFormat implements DateValidator {
  private String dateFormat;

  public DateValidatorWithDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public boolean isValid(String dateStr) {
    DateFormat sdf = new SimpleDateFormat(this.dateFormat);
    sdf.setLenient(false);
    try {
      sdf.parse(dateStr);
    } catch (ParseException e) {
      return false;
    }
    return true;
  }
}
