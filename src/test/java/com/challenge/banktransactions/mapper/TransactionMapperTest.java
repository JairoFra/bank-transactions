package com.challenge.banktransactions.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.dto.TransactionCollectionDto;
import com.challenge.banktransactions.dto.TransactionDto;
import com.challenge.banktransactions.dto.TransactionStatusDto;
import com.challenge.banktransactions.enums.ChannelEnum;
import com.challenge.banktransactions.enums.TransactionStatusEnum;

class TransactionMapperTest {

    private static final String REFERENCE = "11111A";
    private static final String ACCOUNT_IBAN = "ES3501828131359253197773";
    private static final LocalDateTime DATE = LocalDateTime.of(2021, 1, 24, 1, 23);
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(2584.23);
    private static final BigDecimal FEE = BigDecimal.valueOf(2.87);
    private static final String DESCRIPTION = "Payroll";

    TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    @Test
    void testMapEntityToTransactionDto() {
        Transaction transaction = new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, BigDecimal.valueOf(12.17499),
                BigDecimal.valueOf(1.345), DESCRIPTION);

        TransactionDto mappedDto = transactionMapper.mapEntityToTransactionDto(transaction);

        assertEquals(REFERENCE, mappedDto.getReference());
        assertEquals(ACCOUNT_IBAN, mappedDto.getAccountIban());
        assertEquals(DATE, mappedDto.getDate());
        assertEquals(BigDecimal.valueOf(12.17), mappedDto.getAmount());
        assertEquals(BigDecimal.valueOf(1.35), mappedDto.getFee());
    }

    @Test
    void testMapEntityListToTransactionCollectionDto() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction_1 = new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION);
        Transaction transaction_2 = new Transaction("22222B", ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION);
        transactionList.add(transaction_1);
        transactionList.add(transaction_2);

        TransactionCollectionDto mappedDto = transactionMapper.mapEntityListToTransactionCollectionDto(transactionList);

        assertEquals(transaction_1, mappedDto.getTransactions().get(0));
    }

    @Test
    void testMapEntityToTransactionStatusDto() {
        Transaction transaction = new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION);

        MappingJacksonValue mappedDto = transactionMapper.mapEntityToTransactionStatusDto(transaction,
                ChannelEnum.INTERNAL);

        TransactionStatusDto unwrappedDto = (TransactionStatusDto) mappedDto.getValue();

        assertEquals(REFERENCE, unwrappedDto.getReference());
        assertEquals(AMOUNT, unwrappedDto.getAmount());
        assertEquals(FEE, unwrappedDto.getFee());
        assertEquals(TransactionStatusEnum.SETTLED, unwrappedDto.getStatus());
    }

    @Test
    void testMapTransactionDtoForCreation() {
        TransactionDto creationDto = new TransactionDto();
        creationDto.setReference(REFERENCE);
        creationDto.setAccountIban(ACCOUNT_IBAN);
        creationDto.setDate(DATE);
        creationDto.setAmount(BigDecimal.valueOf(12.17499));
        creationDto.setFee(BigDecimal.valueOf(1.345));
        creationDto.setDescription(DESCRIPTION);

        Transaction transaction = transactionMapper.mapTransactionDtoForCreation(creationDto);

        assertEquals(REFERENCE, transaction.getReference());
        assertEquals(ACCOUNT_IBAN, transaction.getAccountIban());
        assertEquals(DATE, transaction.getDate());
        assertEquals(BigDecimal.valueOf(12.17), transaction.getAmount());
        assertEquals(BigDecimal.valueOf(1.35), transaction.getFee());
        assertEquals(DESCRIPTION, transaction.getDescription());
    }

    @Test
    void testMapTransactionDtoForCreation_GenerateValues() {
        TransactionDto creationDto = new TransactionDto();
        creationDto.setAccountIban(ACCOUNT_IBAN);
        creationDto.setAmount(BigDecimal.valueOf(12.17499));
        creationDto.setFee(BigDecimal.valueOf(1.345));
        creationDto.setDescription(DESCRIPTION);

        Transaction transaction = transactionMapper.mapTransactionDtoForCreation(creationDto);

        assertFalse(Strings.isNullOrEmpty(transaction.getReference()));
        assertNotNull(transaction.getDate());
    }

}
