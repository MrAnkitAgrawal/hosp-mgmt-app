package com.nkit.hospmgmtapp.exceptionhandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
  private final ErrorParser errorParser;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    ErrorList errors = errorParser.processAndFormatMethodArgumentError(fieldErrors);
    return new ResponseEntity<>(errors, BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorList> handleRequestValidationErrors(
      ConstraintViolationException ex, WebRequest request) {
    ErrorList errors = errorParser.processAndFormatConstraintViolation(ex);
    return new ResponseEntity<>(errors, BAD_REQUEST);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorList> handleRequestValidationErrors(
      RuntimeException ex, WebRequest request) {
    ErrorList errors = errorParser.processAndFormatRuntimeException(ex);
    return new ResponseEntity<>(errors, BAD_REQUEST);
  }
}
