package com.challenge.banktransactions.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.repository.TransactionRepository;

/**
 * The TransactionService implementation class.
 */
@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Find by reference.
     *
     * @param reference
     *            the reference
     * @return the transaction
     */
    @Override
    public Transaction findByReference(String reference) {
        return transactionRepository.findById(reference).orElse(null);
    }

    /**
     * Search by account IBAN and sort by amount.
     *
     * @param accountIban
     *            the account IBAN
     * @param directionString
     *            the direction string value
     * @return the list
     */
    @Override
    public List<Transaction> searchByAccountIbanSortByAmount(String accountIban, String directionString) {
        // Default direction: ascendant
        Direction direction = directionString != null && directionString.toUpperCase().equals(Direction.DESC.toString())
                ? Direction.DESC
                : Direction.ASC;
        return transactionRepository.findByAccountIban(accountIban, Sort.by(direction, "amount"));
    }

    /**
     * Get the account balance.
     *
     * @param accountIban
     *            the account IBAN
     * @return the account balance
     */
    @Override
    public BigDecimal getAccountBalance(String accountIban) {
        return transactionRepository.getAccountBalance(accountIban);
    }

    /**
     * Save transaction.
     *
     * @param transaction
     *            the transaction
     * @return the transaction
     */
    @Override
    @Transactional(readOnly = false)
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
