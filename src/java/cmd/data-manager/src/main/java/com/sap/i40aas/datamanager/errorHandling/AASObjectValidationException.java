package com.sap.i40aas.datamanager.errorHandling;


public class AASObjectValidationException extends RuntimeException {


  public AASObjectValidationException() {
  }

  public AASObjectValidationException(String message) {
    super(message);
  }

  public AASObjectValidationException(Throwable cause) {
    super(cause);
  }

  public AASObjectValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public AASObjectValidationException(String message, Throwable cause,
                                      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
