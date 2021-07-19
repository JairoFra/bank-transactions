package com.challenge.banktransactions.service;

import java.math.BigDecimal;
import java.util.List;

import com.challenge.banktransactions.domain.Transaction;

/**
 * The Interface TransactionService.
 */
public interface TransactionService {

    /**
     * Find by reference.
     *
     * @param reference
     *            the reference
     * @return the transaction
     */
    Transaction findByReference(String reference);

    /**
     * Search by account IBAN and sort by amount.
     *
     * @param accountIban
     *            the account IBAN
     * @param directionString
     *            the direction string value
     * @return the list
     */
    List<Transaction> searchByAccountIbanSortByAmount(String accountIban, String directionString);

    /**
     * Get the account balance.
     *
     * @param accountIban
     *            the account IBAN
     * @return the account balance
     */
    BigDecimal getAccountBalance(String accountIban);

    /**
     * Save transaction.
     *
     * @param transaction
     *            the transaction
     * @return the transaction
     */
    Transaction save(Transaction transaction);

}
