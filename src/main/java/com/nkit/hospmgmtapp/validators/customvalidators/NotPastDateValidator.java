package com.nkit.hospmgmtapp.validators.customvalidators;

import com.nkit.hospmgmtapp.validators.annotations.NotPastDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.format.DateTimeParseException;

import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseStringToDate;
import static java.time.LocalDate.now;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class NotPastDateValidator implements ConstraintValidator<NotPastDate, String> {
  @Override
  public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
    boolean isValid = false;
    try {
      if (isBlank(date) || !parseStringToDate(date).isBefore(now())) {
        isValid = true;
      }
    } catch (DateTimeParseException ex) {
      isValid = false;
    }
    return isValid;
  }
}
