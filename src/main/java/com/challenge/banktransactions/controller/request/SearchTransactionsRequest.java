package com.challenge.banktransactions.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO used to map the Search Transaction request.
 */
public class SearchTransactionsRequest {

    private String accountIban;

    private String sortAmountDirection;

    @JsonProperty("account_iban")
    public String getAccountIban() {
        return accountIban;
    }

    public void setAccountIban(String accountIban) {
        this.accountIban = accountIban;
    }

    public String getSortAmountDirection() {
        return sortAmountDirection;
    }

    public void setSortAmountDirection(String sortAmountDirection) {
        this.sortAmountDirection = sortAmountDirection;
    }

}
