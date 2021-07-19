package com.challenge.banktransactions.acceptance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import com.challenge.banktransactions.controller.TransactionController;
import com.challenge.banktransactions.controller.request.GetStatusRequest;
import com.challenge.banktransactions.domain.Transaction;
import com.challenge.banktransactions.dto.TransactionStatusDto;
import com.challenge.banktransactions.enums.ChannelEnum;
import com.challenge.banktransactions.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionsTest extends SpringIntegrationTest {

    @LocalServerPort
    private Integer port;

    private static final String BASE_URL = "http://localhost:";

    @Autowired
    TransactionRepository transactionRepository;

    private Transaction transaction;

    private Transaction todayTransaction;
    private Transaction pastTransaction;
    private Transaction futureTransaction;
    // private String reference;
    // private BigDecimal amount;
    // private BigDecimal fee;

    @Before
    public void createTransactions() {
        LocalDateTime today = LocalDateTime.now(ZoneOffset.UTC);

        this.todayTransaction = transactionRepository.save(new Transaction(UUID.randomUUID().toString(),
                "ES3501828131359253197773", today, BigDecimal.valueOf(2584.23), BigDecimal.valueOf(2.87), "Payroll"));

        this.pastTransaction = transactionRepository
                .save(new Transaction(UUID.randomUUID().toString(), "ES3501828131359253197773", today.minusDays(1),
                        BigDecimal.valueOf(2584.23), BigDecimal.valueOf(2.87), "Payroll"));

        this.futureTransaction = transactionRepository
                .save(new Transaction(UUID.randomUUID().toString(), "ES3501828131359253197773", today.plusDays(1),
                        BigDecimal.valueOf(2584.23), BigDecimal.valueOf(2.87), "Payroll"));
    }

    @After
    public void deleteTransactions() {
        transactionRepository.delete(todayTransaction);
        transactionRepository.delete(pastTransaction);
        transactionRepository.delete(futureTransaction);
    }

    @Given("a transaction that is not stored in our system")
    public void transactionNotStored() {
        transaction = new Transaction();
        transaction.setReference("XXXXXX");
    }

    @Given("a transaction that is stored in our system and its date is before today")
    public void transactionStoredWithDateBeforeToday() {
        transaction = pastTransaction;
    }

    @Given("a transaction that is stored in our system and its date is today")
    public void transactionStoredWithDateToday() {
        transaction = todayTransaction;
    }

    @Given("a transaction that is stored in our system and its date is after today")
    public void transactionStoredWithDateAfterToday() {
        transaction = futureTransaction;
    }

    // @Given("a transaction that is stored in our system and its date is before today")
    // public void transactionStoredAndDateBeforeToday() {
    // this.reference = "11111A";
    // this.amount = BigDecimal.valueOf(2584.23);
    // this.fee = BigDecimal.valueOf(2.87);
    // }
    //
    // @Given("a transaction that is stored in our system and its date is equals to today")
    // public void transactionStoredAndDateEqualsToday() {
    // this.reference = "12222J";
    // this.amount = BigDecimal.valueOf(-104.03);
    // this.fee = BigDecimal.valueOf(3.35);
    // }
    //
    // @Given("a transaction that is stored in our system and its date is greater than today")
    // public void transactionStoredAndDateAfterToday() {
    // this.reference = "14444L";
    // this.amount = BigDecimal.valueOf(-72.80);
    // this.fee = BigDecimal.valueOf(1.99);
    // }

    @When("I check the status from {string} channel")
    public void checkStatusFromAnyChannel(String channel) throws IOException {
        GetStatusRequest requestBody = new GetStatusRequest();
        requestBody.setReference(this.transaction.getReference());
        requestBody.setChannel(ChannelEnum.valueOf(channel));

        executePost(BASE_URL + port + TransactionController.CONTROLLER_URL + TransactionController.STATUS_URL,
                requestBody);
    }

    @Then("the system returns the status {string}")
    public void returnsStatus(String expectedStatus) throws JsonParseException, JsonMappingException, IOException {
        TransactionStatusDto bodyObject = new ObjectMapper().readValue(latestResponse.getBody(),
                TransactionStatusDto.class);
        assertThat("Status is " + expectedStatus, bodyObject.getStatus().toString(), is(expectedStatus));
    }

    @And("the system returns the amount substracting the fee")
    public void returnsAmountSubstractingFee() throws JsonParseException, JsonMappingException, IOException {
        TransactionStatusDto bodyObject = new ObjectMapper().readValue(latestResponse.getBody(),
                TransactionStatusDto.class);
        assertThat("Amount minus fee returned", bodyObject.getAmount(),
                is(this.transaction.getAmount().subtract(this.transaction.getFee()).setScale(2, RoundingMode.HALF_UP)));
        assertNull(bodyObject.getFee());
    }

    @And("the system returns the amount and the fee")
    public void returnsAmountAndFee() throws JsonParseException, JsonMappingException, IOException {
        TransactionStatusDto bodyObject = new ObjectMapper().readValue(latestResponse.getBody(),
                TransactionStatusDto.class);
        assertThat("Amount returned", bodyObject.getAmount(),
                is(this.transaction.getAmount().setScale(2, RoundingMode.HALF_UP)));
        assertThat("Fee returned", bodyObject.getFee(),
                is(this.transaction.getFee().setScale(2, RoundingMode.HALF_UP)));
    }
}
