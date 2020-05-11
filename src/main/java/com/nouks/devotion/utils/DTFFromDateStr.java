package com.nouks.devotion.utils;

import java.time.format.DateTimeFormatter;

public class DTFFromDateStr {
    public static DateTimeFormatter getDateTimeFormatterFromString(String dateTime) {
        if(dateTime.contains(":") && dateTime.contains("-")) {
            String t = dateTime.substring(dateTime.indexOf(":") + 1);
            if(t.contains(":")) {
                // the time string has seconds
                return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            } else {
                // the time string does not have seconds but has only minutes
                return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            }
        } else if (dateTime.contains("-")){
            // time string does not have minutes nor seconds
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        } else {
            return DateTimeFormatter.BASIC_ISO_DATE;
        }
    }
}
