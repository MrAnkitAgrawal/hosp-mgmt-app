package com.nkit.hospmgmtapp.validators.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.nkit.hospmgmtapp.validators.customvalidators.NotPastDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NotPastDateValidator.class)
public @interface NotPastDate {
  String message() default "Invalid value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
