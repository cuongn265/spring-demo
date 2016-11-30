package com.eugene.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Eugene on 11/29/2016.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ AccessDeniedException.class })
  public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
    return new ResponseEntity<Object>(
      "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
  }
}
