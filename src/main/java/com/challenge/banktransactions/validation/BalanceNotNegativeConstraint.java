package com.challenge.banktransactions.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Interface for validation of balance not negative.
 */
@Documented
@Constraint(validatedBy = BalanceNotNegativeValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface BalanceNotNegativeConstraint {

    String message() default "The account does not have enough funds to process this transaction.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
