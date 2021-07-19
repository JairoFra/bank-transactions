package com.challenge.banktransactions.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.banktransactions.controller.request.GetStatusRequest;
import com.challenge.banktransactions.controller.request.SearchTransactionsRequest;
import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.dto.TransactionCollectionDto;
import com.challenge.banktransactions.dto.TransactionDto;
import com.challenge.banktransactions.dto.TransactionStatusDto;
import com.challenge.banktransactions.enums.TransactionStatusEnum;
import com.challenge.banktransactions.mapper.TransactionMapper;
import com.challenge.banktransactions.service.TransactionService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * The Class TransactionController.
 */
@RestController
@RequestMapping(TransactionController.CONTROLLER_URL)
public class TransactionController {

    public static final String CONTROLLER_URL = "/transactions";
    public static final String CREATE_URL = "/create";
    public static final String SEARCH_URL = "/search";
    public static final String STATUS_URL = "/get-status";

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    /**
     * Creates the transaction.
     *
     * @param transactionDto
     *            the transaction dto
     * @return the mapping jackson value
     *         adapts a TransactionStatusDto object with the appropriate fields
     */
    @PostMapping(CREATE_URL)
    public MappingJacksonValue createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        // We assume that the date will come in UTC
        Transaction transaction = transactionService
                .save(transactionMapper.mapTransactionDtoForCreation(transactionDto));

        return transactionMapper.mapEntityToTransactionStatusDto(transaction, null);
    }

    /**
     * Search transactions.
     *
     * @param request
     *            - account_iban: IBAN of the account to search
     *            - sortAmountDirection: "ASC" (sorted from greater to lower amount) or "DESC" (sorted from lower to
     *            greater amount). Optional field. Default value is "ASC".
     * @return the transaction collection dto
     */
    @PostMapping(SEARCH_URL)
    public TransactionCollectionDto searchTransactions(@RequestBody SearchTransactionsRequest request) {
        List<Transaction> transactions = transactionService.searchByAccountIbanSortByAmount(request.getAccountIban(),
                request.getSortAmountDirection());

        return transactionMapper.mapEntityListToTransactionCollectionDto(transactions);
    }

    /**
     * Gets the status of a transaction.
     *
     * @param request
     *            - reference: reference of the transactions requested
     *            - channel: "ATM", "CLIENT" or "INTERNAL". Optional field. Default value is "INTERNAL".
     * @return the status transaction
     */
    @PostMapping(STATUS_URL)
    public MappingJacksonValue getStatusTransaction(@RequestBody GetStatusRequest request) {
        Transaction transaction = transactionService.findByReference(request.getReference());

        if (transaction == null) {
            TransactionStatusDto transactionStatusDto = new TransactionStatusDto(request.getReference(),
                    TransactionStatusEnum.INVALID);

            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("reference", "status");
            FilterProvider filters = new SimpleFilterProvider().addFilter("FilterStatus", filter);
            MappingJacksonValue mapping = new MappingJacksonValue(transactionStatusDto);
            mapping.setFilters(filters);

            return mapping;
        } else {
            return transactionMapper.mapEntityToTransactionStatusDto(transaction, request.getChannel());
        }
    }

}
