package com.sap.i40aas.datamanager.webService.controllers;


public class DuplicateResourceException extends Exception {

  private static final long serialVersionUID = 1997753363232807009L;

  public DuplicateResourceException() {
  }

  public DuplicateResourceException(String message) {
    super(message);
  }

  public DuplicateResourceException(Throwable cause) {
    super(cause);
  }

  public DuplicateResourceException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateResourceException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
