package com.nouks.devotion.utils;

import com.nouks.devotion.exceptions.ApiException;
import com.nouks.devotion.exceptions.ErrorCodes;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
  private static DateTimeFormatter getDateTimeFormatterFromString(String time) {
    if(time.trim().contains(":")) {
      String t = time.substring(time.indexOf(":") + 1);
      if(t.contains(":")) {
        // the time string has seconds
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      } else {
        // the time string does not have seconds but has only minutes
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      }
    } else {
      // time string does not have minutes nor seconds
      return DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
    }
  }
  public static LocalDateTime parseString(String time) {
    try {
      return LocalDateTime.parse(time, getDateTimeFormatterFromString(time));
    } catch (Exception ex) {
      throw new ApiException(ErrorCodes.INVALID_FORMAT.getMessage() + " invalid date time format",
        HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.INVALID_FORMAT.toString());
    }

  }
}
