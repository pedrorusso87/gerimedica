package com.pedro.assignment.controller.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ItemNotFoundException.class)
  protected ResponseEntity<Object> handleBadCredentialsException(ItemNotFoundException ex) {
    return new ResponseEntity<>("No item found for that code",HttpStatus.BAD_REQUEST);
  }
}
