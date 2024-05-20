package com.nkit.hospmgmtapp.utils;

import static com.nkit.hospmgmtapp.utils.HospMgmtConstants.DATE_PATTERN;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.time.LocalDate;

public class HospMgmtUtils {
  private HospMgmtUtils() {
    throw new UnsupportedOperationException();
  }

  public static LocalDate parseStringToDate(String dateStr) {
    if (isEmpty(dateStr)) {
      return null;
    }
    return parse(dateStr, ofPattern(DATE_PATTERN));
  }

  public static String parseDateToString(LocalDate date) {
    if (date == null) {
      return null;
    }
    return date.format(ofPattern(DATE_PATTERN));
  }
}
