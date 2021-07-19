package com.challenge.banktransactions.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.challenge.banktransactions.validation.ValidationUtils;

/**
 * Transaction entity class.
 */
@Entity
public class Transaction {

    @Id
    @Column(nullable = false, unique = true, length = ValidationUtils.MAX_LENGTH_REFERENCE)
    private String reference;

    @Column(nullable = false)
    private String accountIban;

    @Column(nullable = false)

    private LocalDateTime date;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal fee;

    @Column(length = ValidationUtils.MAX_LENGTH_REFERENCE)
    private String description;

    public Transaction() {}

    public Transaction(String reference, String accountIban, LocalDateTime date, BigDecimal amount, BigDecimal fee,
            String description) {
        this.reference = reference;
        this.accountIban = accountIban;
        this.date = date;
        this.amount = amount;
        this.fee = fee;
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reference == null) ? 0 : reference.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (reference == null) {
            if (other.reference != null)
                return false;
        } else if (!reference.equals(other.reference)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transaction [reference=" + reference + ", accountIban=" + accountIban + ", date=" + date + ", amount="
                + amount + ", fee=" + fee + ", description=" + description + "]";
    }
}
