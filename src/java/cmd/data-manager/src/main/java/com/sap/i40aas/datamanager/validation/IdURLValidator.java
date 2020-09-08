package com.sap.i40aas.datamanager.validation;

import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/*
ConstraintValidator defines the logic to validate a given constraint for a given object.
  Implementations must comply with the following restriction:
  the object must resolve to a non-parametrized type
  generic parameters of the object must be unbounded wildcard types
*/

public class IdURLValidator implements
  ConstraintValidator<IdURLConstraint, String> {

  @Override
  public void initialize(IdURLConstraint contactNumber) {
  }

  @Override
  public boolean isValid(String aasObj_id,
                         ConstraintValidatorContext cxt) {
    String[] schemes = {"http", "https"};

    UrlValidator urlValidator = new UrlValidator(schemes);
    if (!urlValidator.isValid(aasObj_id)) {
      System.out.println("url is invalid");
      return false;
    } else {
      System.out.println("url is valid");
      return true;
    }
  }

}
