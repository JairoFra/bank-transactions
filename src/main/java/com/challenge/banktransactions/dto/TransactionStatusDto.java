package com.challenge.banktransactions.dto;

import java.math.BigDecimal;

import com.challenge.banktransactions.enums.TransactionStatusEnum;
import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Transaction Status DTO
 */
@JsonFilter("FilterStatus")
public class TransactionStatusDto {

    private String reference;
    private BigDecimal amount;
    private BigDecimal fee;
    private TransactionStatusEnum status;

    public TransactionStatusDto() {}

    public TransactionStatusDto(String reference) {
        this.reference = reference;
    }

    public TransactionStatusDto(String reference, TransactionStatusEnum status) {
        this.reference = reference;
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

}
