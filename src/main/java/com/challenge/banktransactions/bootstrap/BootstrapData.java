package com.challenge.banktransactions.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.repository.TransactionRepository;

/**
 * Bootstrap class to create initial entries to the database.
 */
@Component
public class BootstrapData implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public BootstrapData(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void run(String... args) {
        List<Transaction> initialTransactions = new ArrayList<>();

        initialTransactions
                .add(new Transaction("11111A", "ES3501828131359253197773", LocalDateTime.of(2021, 1, 24, 1, 23),
                        BigDecimal.valueOf(2584.23), BigDecimal.valueOf(2.87), "Payroll"));
        initialTransactions.add(new Transaction("22222B", "ES4131906372378889866419",
                LocalDateTime.of(2021, 2, 9, 0, 15), BigDecimal.valueOf(1565.45), BigDecimal.valueOf(1.34), "Payroll"));
        initialTransactions
                .add(new Transaction("33333C", "ES7331907963457783558325", LocalDateTime.of(2021, 3, 16, 8, 12),
                        BigDecimal.valueOf(2243.26), BigDecimal.valueOf(2.73), "Payroll"));
        initialTransactions
                .add(new Transaction("44444D", "ES7600759661145476623415", LocalDateTime.of(2021, 3, 20, 4, 45),
                        BigDecimal.valueOf(3176.09), BigDecimal.valueOf(2.25), "Payroll"));
        initialTransactions
                .add(new Transaction("55555E", "ES3501828131359253197773", LocalDateTime.of(2021, 4, 11, 2, 22),
                        BigDecimal.valueOf(1685.18), BigDecimal.valueOf(2.09), "Payroll"));
        initialTransactions
                .add(new Transaction("66666F", "ES4131906372378889866419", LocalDateTime.of(2021, 5, 18, 4, 45),
                        BigDecimal.valueOf(-345.73), BigDecimal.valueOf(2.37), "Clothing"));
        initialTransactions.add(new Transaction("77777G", "ES7331907963457783558325",
                LocalDateTime.of(2021, 6, 9, 2, 22), BigDecimal.valueOf(-344.98), BigDecimal.valueOf(1.89), "Flight"));
        initialTransactions.add(new Transaction("88888H", "ES3501828131359253197773",
                LocalDateTime.of(2021, 6, 15, 2, 22), BigDecimal.valueOf(-23.73), BigDecimal.valueOf(1.73), "Food"));
        initialTransactions.add(new Transaction("99999I", "ES4131906372378889866419",
                LocalDateTime.of(2021, 6, 24, 8, 12), BigDecimal.valueOf(-34.36), BigDecimal.valueOf(2.08), "Food"));
        initialTransactions.add(new Transaction("12222J", "ES7331907963457783558325",
                LocalDateTime.of(2021, 7, 17, 2, 22), BigDecimal.valueOf(-104.03), BigDecimal.valueOf(3.35), "Taxes"));
        initialTransactions
                .add(new Transaction("13333K", "ES7600759661145476623415", LocalDateTime.of(2021, 9, 2, 4, 45),
                        BigDecimal.valueOf(-67.95), BigDecimal.valueOf(2.06), "Loan payback"));
        initialTransactions.add(new Transaction("14444L", "ES4131906372378889866419",
                LocalDateTime.of(2021, 9, 11, 8, 12), BigDecimal.valueOf(-72.80), BigDecimal.valueOf(1.99), "Books"));

        initialTransactions.forEach(transactionRepository::save);
    }

}
