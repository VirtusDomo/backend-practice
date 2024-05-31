package com.virtusdev.backend_practice.transaction;


import org.aspectj.lang.annotation.Before;
import org.checkerframework.checker.i18n.qual.UnknownLocalized;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionControllerIntTest {

    @LocalServerPort
    int randomServerPort;

    RestClient restClient;

    @BeforeEach
    void setUp(){
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + randomServerPort)
                .build();
    }

    @Test
    @Order(1)
    void shouldFindAllTransactions(){
        List<Transaction> transactions = restClient.get()
                .uri("/api/transactions")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Transaction>>() {});
        assertEquals(20, transactions.size());
    }

    @Test
    @Order(2)
    void shouldFindOneTransaction(){
        Transaction transaction = restClient.get()
                .uri("/api/transactions/{id}", 1)
                .retrieve()
                .body(Transaction.class);

        assertNotNull(transaction);
        assertEquals(1, transaction.id());
        assertEquals(101, transaction.buyerId());
    }

    @Test
    void shouldCreateTransaction(){
        Transaction transaction = new Transaction(21, 105, 120, 321, BigDecimal.valueOf(50.00),LocalDateTime.now());

        ResponseEntity<Transaction> responseEntity = restClient.post()
                .uri("/api/transactions")
                .body(transaction)
                .retrieve()
                .toEntity(Transaction.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Transaction created = restClient.get()
                .uri("/api/transactions/{id}", 21)
                .retrieve()
                .body(Transaction.class);
        assertNotNull(created);
        assertEquals(105, transaction.buyerId());
    }

    @Test
    void shouldUpdateTransaction(){
        Transaction updated = new Transaction(1, 102, 201, 301, BigDecimal.valueOf(70.00), LocalDateTime.now());
        restClient.put()
                .uri("/api/transactions/{id}", 1)
                .body(updated)
                .retrieve()
                .toEntity(Transaction.class);

        Transaction verify = restClient.get()
                .uri("/api/transactions/{id}", 1)
                .retrieve()
                .body(Transaction.class);

        assertEquals(102, verify.buyerId());

    }

    @Test
    void shouldDeleteTransaction(){
        restClient.delete()
                .uri("/api/transactions/{id}", 1)
                .retrieve()
                .toBodilessEntity();
        List<Transaction> transactions = restClient.get()
                .uri("/api/transactions")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Transaction>>() {});

        assertEquals(19, transactions.size());

    }

    @Test
    void shouldReturnTransactionsByBuyerId(){
        List<Transaction> transactions = restClient.get()
                .uri("/api/transactions/buyer/{buyerId}", 101)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Transaction>>() {});

        assertEquals(2, transactions.size());
    }

    @Test
    void shouldReturnTransactionBySellerId(){
        List<Transaction> transactions = restClient.get()
                .uri("/api/transactions/seller/{sellerId}",203)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Transaction>>() {});


        assertEquals(2, transactions.size());
    }


}
