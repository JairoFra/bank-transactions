package com.challenge.banktransactions.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import com.challenge.banktransactions.controller.request.GetStatusRequest;
import com.challenge.banktransactions.controller.request.SearchTransactionsRequest;
import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.dto.TransactionCollectionDto;
import com.challenge.banktransactions.dto.TransactionDto;
import com.challenge.banktransactions.dto.TransactionStatusDto;
import com.challenge.banktransactions.enums.TransactionStatusEnum;
import com.challenge.banktransactions.exception.ValidationExceptionHandler;
import com.challenge.banktransactions.mapper.TransactionMapper;
import com.challenge.banktransactions.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    private static final String REFERENCE = "11111A";
    private static final String ACCOUNT_IBAN = "ES3501828131359253197773";
    private static final LocalDateTime DATE = LocalDateTime.of(2021, 1, 24, 1, 23);
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(2584.23);
    private static final BigDecimal FEE = BigDecimal.valueOf(2.87);
    private static final String DESCRIPTION = "Payroll";

    @Mock
    TransactionService transactionService;

    @Mock
    TransactionMapper transactionMapper;

    @InjectMocks
    TransactionController transactionController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new ValidationExceptionHandler()).setValidator(mock(Validator.class)).build();
    }

    @Test
    void testCreateTransaction() throws Exception {
        TransactionDto creationDto = new TransactionDto();
        creationDto.setReference(REFERENCE);
        creationDto.setAccountIban(ACCOUNT_IBAN);
        creationDto.setDate(DATE);
        creationDto.setAmount(AMOUNT);
        creationDto.setFee(FEE);
        creationDto.setDescription(DESCRIPTION);

        TransactionStatusDto returnDto = new TransactionStatusDto(REFERENCE);
        FilterProvider filters = new SimpleFilterProvider().addFilter("FilterStatus",
                SimpleBeanPropertyFilter.serializeAll());
        MappingJacksonValue returnMapping = new MappingJacksonValue(returnDto);
        returnMapping.setFilters(filters);

        Transaction transaction = new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION);

        when(transactionMapper.mapTransactionDtoForCreation(any())).thenReturn(transaction);
        when(transactionService.save(any())).thenReturn(transaction);
        when(transactionMapper.mapEntityToTransactionStatusDto(any(), any())).thenReturn(returnMapping);

        mockMvc.perform(post(TransactionController.CONTROLLER_URL + TransactionController.CREATE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(creationDto))).andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").value(REFERENCE));
    }

    @Test
    void testSearchTransactions() throws Exception {
        SearchTransactionsRequest request = new SearchTransactionsRequest();
        request.setAccountIban(ACCOUNT_IBAN);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(REFERENCE, ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION));
        transactions.add(new Transaction("22222B", ACCOUNT_IBAN, DATE, AMOUNT, FEE, DESCRIPTION));

        when(transactionService.searchByAccountIbanSortByAmount(anyString(), any())).thenReturn(transactions);
        when(transactionMapper.mapEntityListToTransactionCollectionDto(any()))
                .thenReturn(new TransactionCollectionDto(transactions));

        mockMvc.perform(post(TransactionController.CONTROLLER_URL + TransactionController.SEARCH_URL)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(request))).andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", hasSize(transactions.size())))
                .andExpect(jsonPath("$.transactions[0].reference").value(REFERENCE));
    }

    @Test
    void testGetStatusTransaction() throws Exception {
        GetStatusRequest request = new GetStatusRequest();
        request.setReference(REFERENCE);

        TransactionStatusDto returnDto = new TransactionStatusDto(REFERENCE, TransactionStatusEnum.PENDING);
        FilterProvider filters = new SimpleFilterProvider().addFilter("FilterStatus",
                SimpleBeanPropertyFilter.serializeAll());
        MappingJacksonValue returnMapping = new MappingJacksonValue(returnDto);
        returnMapping.setFilters(filters);

        when(transactionService.findByReference(anyString())).thenReturn(new Transaction());
        when(transactionMapper.mapEntityToTransactionStatusDto(any(), any())).thenReturn(returnMapping);

        mockMvc.perform(post(TransactionController.CONTROLLER_URL + TransactionController.STATUS_URL)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(request))).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(TransactionStatusEnum.PENDING.toString()));
    }

    public static String asJsonString(final Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
