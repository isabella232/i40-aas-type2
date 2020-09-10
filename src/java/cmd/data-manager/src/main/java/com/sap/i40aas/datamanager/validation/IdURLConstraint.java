package com.sap.i40aas.datamanager.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdURLValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdURLConstraint {
  String message() default "Invalid URL as Identification";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
