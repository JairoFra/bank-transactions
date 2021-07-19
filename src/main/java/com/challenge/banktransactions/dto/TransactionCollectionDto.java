package com.challenge.banktransactions.dto;

import java.util.List;

import com.challenge.banktransactions.domain.Transaction;

/**
 * Transaction Collection DTO
 * 
 * Wraps the List of TransactionDto in an object to allow changes in the data returned with retrocompatibility
 * and to make it easier for the client to consume the service.
 */
public class TransactionCollectionDto {

    private List<Transaction> transactions;

    public TransactionCollectionDto() {}

    public TransactionCollectionDto(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
