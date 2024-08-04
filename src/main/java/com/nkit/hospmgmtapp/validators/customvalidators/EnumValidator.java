package com.nkit.hospmgmtapp.validators.customvalidators;

import com.nkit.hospmgmtapp.validators.annotations.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
  private Set<String> validEnumList;

  @Override
  public void initialize(ValidEnum targetEnum) {
    Class<? extends Enum> enumSelected = targetEnum.targetClassType();
    validEnumList =
        (Set<String>)
            EnumSet.allOf(enumSelected).stream()
                .map(e -> ((Enum<? extends Enum<?>>) e).name())
                .collect(toSet());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return value == null || validEnumList.contains(value);
  }
}
