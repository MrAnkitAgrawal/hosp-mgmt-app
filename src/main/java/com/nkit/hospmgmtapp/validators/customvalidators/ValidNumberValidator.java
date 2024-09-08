package com.nkit.hospmgmtapp.validators.customvalidators;

import static org.apache.commons.lang3.StringUtils.*;

import com.nkit.hospmgmtapp.validators.annotations.ValidNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidNumberValidator implements ConstraintValidator<ValidNumber, String> {
  private Integer length;

  @Override
  public void initialize(ValidNumber constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    length = constraintAnnotation.length();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
      return isBlank(value) || (isNumericSpace(value) && length(getDigits(value)) <= length);
  }
}
