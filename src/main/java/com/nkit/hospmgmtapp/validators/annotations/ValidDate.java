package com.nkit.hospmgmtapp.validators.annotations;

import com.nkit.hospmgmtapp.validators.customvalidators.DateValidator;
import com.nkit.hospmgmtapp.validators.customvalidators.NotPastDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface ValidDate {
  String message() default "Invalid value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
