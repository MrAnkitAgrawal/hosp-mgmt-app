package com.nkit.hospmgmtapp.validators.customvalidators;

import com.nkit.hospmgmtapp.validators.annotations.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeParseException;

import static com.nkit.hospmgmtapp.utils.HospMgmtUtils.parseStringToDate;
import static java.time.LocalDate.now;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
  @Override
  public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
    if (isBlank(date)) {
      return true;
    }
    try {
      parseStringToDate(date);
    } catch (DateTimeParseException ex) {
      return false;
    }
    return true;
  }
}
