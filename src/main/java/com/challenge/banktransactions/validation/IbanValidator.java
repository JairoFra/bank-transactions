package com.challenge.banktransactions.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation for validation of IBAN.
 */
public class IbanValidator implements ConstraintValidator<IbanConstraint, String> {

    @Autowired
    private IBANValidator ibanHelper;

    @Override
    public void initialize(IbanConstraint ibanConstraint) {}

    @Override
    public boolean isValid(String accountIban, ConstraintValidatorContext cxt) {
        return ibanHelper.isValid(accountIban);
    }

}
