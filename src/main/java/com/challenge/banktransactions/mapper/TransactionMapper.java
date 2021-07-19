package com.challenge.banktransactions.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.dto.TransactionCollectionDto;
import com.challenge.banktransactions.dto.TransactionDto;
import com.challenge.banktransactions.dto.TransactionStatusDto;
import com.challenge.banktransactions.enums.ChannelEnum;
import com.challenge.banktransactions.enums.TransactionStatusEnum;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * The Interface TransactionMapper.
 */
@Mapper
public interface TransactionMapper {

    @Mapping(source = "amount", target = "amount", qualifiedByName = "currencyMapper")
    @Mapping(source = "fee", target = "fee", qualifiedByName = "currencyMapper")
    TransactionDto mapEntityToTransactionDto(Transaction transaction);

    @Mapping(source = "reference", target = "reference", qualifiedByName = "referenceMapper")
    @Mapping(source = "date", target = "date", qualifiedByName = "dateMapper")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "currencyMapper")
    @Mapping(source = "fee", target = "fee", qualifiedByName = "currencyMapper")
    Transaction mapTransactionDtoForCreation(TransactionDto transactionDto);

    default MappingJacksonValue mapEntityToTransactionStatusDto(Transaction transaction, ChannelEnum channel) {

        TransactionStatusDto transactionStatusDto = new TransactionStatusDto(transaction.getReference());

        // If channel is empty we assume it is INTERNAL (default value)
        if (channel == null) {
            channel = ChannelEnum.INTERNAL;
        }

        // Set status
        LocalDate transactionDate = transaction.getDate().toLocalDate();
        LocalDate today = LocalDate.now(ZoneOffset.UTC);

        if (transactionDate.isBefore(today)) {
            transactionStatusDto.setStatus(TransactionStatusEnum.SETTLED);
        } else if (transactionDate.isEqual(today)) {
            transactionStatusDto.setStatus(TransactionStatusEnum.PENDING);
        } else {
            transactionStatusDto.setStatus(
                    channel.equals(ChannelEnum.ATM) ? TransactionStatusEnum.PENDING : TransactionStatusEnum.FUTURE);
        }

        // Set amount and fee
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();

        if (channel.equals(ChannelEnum.INTERNAL)) {
            transactionStatusDto.setAmount(mapCurrency(transaction.getAmount()));
            transactionStatusDto.setFee(mapCurrency(transaction.getFee()));
        } else {
            transactionStatusDto.setAmount(mapCurrency(transaction.getAmount().subtract(transaction.getFee())));
            filter = SimpleBeanPropertyFilter.serializeAllExcept("fee");
        }

        FilterProvider filters = new SimpleFilterProvider().addFilter("FilterStatus", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(transactionStatusDto);

        mapping.setFilters(filters);

        return mapping;
    }

    default TransactionCollectionDto mapEntityListToTransactionCollectionDto(List<Transaction> transactions) {
        return new TransactionCollectionDto(transactions);
    }

    @Named("referenceMapper")
    public static String mapReference(String reference) {
        // If reference value is null or blank, a new reference is generated
        if (reference == null || reference.isBlank()) {
            reference = UUID.randomUUID().toString();
        }

        return reference;
    }

    @Named("dateMapper")
    public static LocalDateTime mapDate(LocalDateTime date) {
        // If date comes null in the request, the current date is used
        return date == null ? LocalDateTime.now(ZoneOffset.UTC) : date;
    }

    @Named("currencyMapper")
    public static BigDecimal mapCurrency(BigDecimal amount) {
        amount = amount == null ? BigDecimal.ZERO : amount;
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

}
