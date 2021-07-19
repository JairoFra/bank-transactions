package com.challenge.banktransactions.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.banktransactions.domain.Transaction;

/**
 * The Interface TransactionRepository.
 */
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByAccountIban(String accountIban, Sort sort);

    @Query(value = "SELECT ISNULL(SUM(amount - fee), 0) FROM Transaction WHERE account_iban = ?1", nativeQuery = true)
    BigDecimal getAccountBalance(String accountIban);
}
