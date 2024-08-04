package com.nkit.hospmgmtapp.validators.annotations;

import com.nkit.hospmgmtapp.validators.customvalidators.EnumValidator;
import com.nkit.hospmgmtapp.validators.customvalidators.ValidDialysisClosureStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface InvalidEnum {
  String message() default "Invalid value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends Enum<?>> targetClassType();
}
