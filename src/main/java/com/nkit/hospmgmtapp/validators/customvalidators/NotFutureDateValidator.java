package com.nkit.hospmgmtapp.validators.customvalidators;

import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseStringToDate;
import static java.time.LocalDate.now;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.nkit.hospmgmtapp.validators.annotations.NotFutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.format.DateTimeParseException;

public class NotFutureDateValidator implements ConstraintValidator<NotFutureDate, String> {
  @Override
  public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
    boolean isValid = false;
    try {
      if (isBlank(date) || !parseStringToDate(date).isAfter(now())) {
        isValid = true;
      }
    } catch (DateTimeParseException ex) {
      isValid = false;
    }
    return isValid;
  }
}
