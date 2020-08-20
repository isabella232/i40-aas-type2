package com.sap.i40aas.datamanager.webService.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

// works at the @Controller level –
// define a method to handle exceptions, and annotate that with
// @ExceptionHandler:

  @ExceptionHandler(value
    = {java.util.NoSuchElementException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleNoResourceFound(
    RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Requested resource not found";
    return handleExceptionInternal(ex, bodyOfResponse,
      new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }
}
