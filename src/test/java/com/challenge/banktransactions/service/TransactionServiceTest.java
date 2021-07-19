package com.challenge.banktransactions.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private static final String REFERENCE = "11111A";
    private static final String ACCOUNT_IBAN = "ES3501828131359253197773";
    private static final LocalDateTime DATE = LocalDateTime.of(2021, 1, 24, 1, 23);
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(2584.23);
    private static final BigDecimal FEE = BigDecimal.valueOf(2.87);
    private static final String DESCRIPTION = "Payroll";

    @Mock
    TransactionRepository transactionRepository;

    TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void testFindByReference() {
        Transaction transaction = new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION);

        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(transaction));

        Transaction transactionReturned = transactionService.findByReference(REFERENCE);

        assertEquals(REFERENCE, transactionReturned.getReference());
    }

    @Test
    void testFindByReference_NotFound() {
        when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());

        Transaction returnTransaction = transactionService.findByReference(REFERENCE);

        assertNull(returnTransaction);
    }

    @Test
    void testSearchByAccountIbanSortByAmount() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION));
        transactionList.add(new Transaction("22222B", ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION));

        when(transactionRepository.findByAccountIban(anyString(), any())).thenReturn(transactionList);

        List<Transaction> returnTransactionList = transactionService.searchByAccountIbanSortByAmount(ACCOUNT_IBAN,
                "DESC");

        assertEquals(transactionList.size(), returnTransactionList.size());
    }

    @Test
    void testSave() {
        Transaction transaction = new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION);

        when(transactionRepository.save(any())).thenReturn(transaction);

        Transaction returnTransaction = transactionService.save(transaction);

        assertEquals(REFERENCE, returnTransaction.getReference());
    }

    @Test
    void testGetAccountBalance() {
        when(transactionRepository.getAccountBalance(anyString())).thenReturn(AMOUNT.subtract(FEE));

        BigDecimal balanceResult = transactionService.getAccountBalance(ACCOUNT_IBAN);

        assertEquals(AMOUNT.subtract(FEE), balanceResult);
    }

}
