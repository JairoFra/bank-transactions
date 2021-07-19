package com.challenge.banktransactions.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.challenge.banktransactions.dto.TransactionDto;
import com.challenge.banktransactions.service.TransactionService;

/**
 * Implementation for validation of balance not negative.
 */
public class BalanceNotNegativeValidator implements ConstraintValidator<BalanceNotNegativeConstraint, TransactionDto> {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void initialize(BalanceNotNegativeConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(TransactionDto transactionDto, ConstraintValidatorContext context) {
        BigDecimal amount = transactionDto.getAmount() == null ? BigDecimal.ZERO : transactionDto.getAmount();
        BigDecimal fee = transactionDto.getFee() == null ? BigDecimal.ZERO : transactionDto.getFee();

        BigDecimal currBalance = transactionService.getAccountBalance(transactionDto.getAccountIban());
        return currBalance.add(amount).subtract(fee).compareTo(BigDecimal.ZERO) > 0;
    }

}
