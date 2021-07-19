package com.challenge.banktransactions.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.challenge.banktransactions.service.TransactionService;

/**
 * Implementation for validation of unique reference.
 */
public class ReferenceUniqueValidator implements ConstraintValidator<ReferenceUniqueConstraint, String> {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void initialize(ReferenceUniqueConstraint referenceUniqueConstraint) {}

    @Override
    public boolean isValid(String reference, ConstraintValidatorContext cxt) {
        if (reference == null || reference.isBlank()) {
            return true;
        }

        return transactionService.findByReference(reference) == null;
    }

}
