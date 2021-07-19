package com.challenge.banktransactions.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Interface for validation of IBAN.
 */
@Documented
@Constraint(validatedBy = IbanValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IbanConstraint {
    String message() default "The IBAN of the account does not have a valid format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
