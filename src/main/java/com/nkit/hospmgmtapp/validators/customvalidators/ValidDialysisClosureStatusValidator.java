package com.nkit.hospmgmtapp.validators.customvalidators;

import static java.util.stream.Collectors.toSet;

import com.nkit.hospmgmtapp.validators.annotations.ValidDialysisClosureStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.EnumSet;
import java.util.Set;

public class ValidDialysisClosureStatusValidator
    implements ConstraintValidator<ValidDialysisClosureStatus, String> {
  private Set<String> allowedDialysisStatuses;

  @Override
  public void initialize(ValidDialysisClosureStatus targetEnum) {
    Class<? extends Enum> enumSelected = targetEnum.targetClassType();
    allowedDialysisStatuses =
        (Set<String>)
            EnumSet.allOf(enumSelected).stream()
                .map(e -> ((Enum<? extends Enum<?>>) e).name())
                .collect(toSet());
    allowedDialysisStatuses.remove("AVAILABLE");
    allowedDialysisStatuses.remove("SCHEDULED");
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return value == null || allowedDialysisStatuses.contains(value);
  }
}
