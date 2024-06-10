package com.nkit.hospmgmtapp.exceptionhandler;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ErrorParser {
  public ErrorList processAndFormatMethodArgumentError(List<FieldError> fieldErrors) {
    return new ErrorList(
        fieldErrors.stream()
            .map(
                e ->
                    new Error(
                        e.getDefaultMessage(), e.getDefaultMessage(), null, BAD_REQUEST.value()))
            .collect(toList()));
  }

  public ErrorList processAndFormatConstraintViolation(ConstraintViolationException ex) {
    return new ErrorList(
        ex.getConstraintViolations().stream()
            .map(e -> new Error(e.getMessage(), e.getMessage(), null, BAD_REQUEST.value()))
            .collect(toList()));
  }

  public ErrorList processAndFormatRuntimeException(RuntimeException ex) {
    return new ErrorList(
            List.of(new Error(ex.getMessage(), ex.getMessage(), null, BAD_REQUEST.value())));
  }
}
