package com.challenge.banktransactions.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.challenge.banktransactions.validation.BalanceNotNegativeConstraint;
import com.challenge.banktransactions.validation.IbanConstraint;
import com.challenge.banktransactions.validation.ReferenceUniqueConstraint;
import com.challenge.banktransactions.validation.ValidationUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Transaction DTO
 */
@BalanceNotNegativeConstraint
public class TransactionDto {

    @ReferenceUniqueConstraint
    @Size(max = ValidationUtils.MAX_LENGTH_REFERENCE, message = "The reference cannot be larger than "
            + ValidationUtils.MAX_LENGTH_REFERENCE + " characters.")
    private String reference;

    @IbanConstraint
    private String accountIban;

    private LocalDateTime date;

    @NotNull(message = "The amount cannot be null.")
    private BigDecimal amount;

    @PositiveOrZero(message = "The transaction fee cannot be negative.")
    private BigDecimal fee;

    @Size(max = ValidationUtils.MAX_LENGTH_DEFAULT, message = "The description cannot be larger than "
            + ValidationUtils.MAX_LENGTH_DEFAULT + " characters.")
    private String description;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @JsonProperty("account_iban")
    public String getAccountIban() {
        return accountIban;
    }

    public void setAccountIban(String accountIban) {
        this.accountIban = accountIban;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
