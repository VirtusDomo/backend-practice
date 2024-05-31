package com.virtusdev.backend_practice.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcTransactionRepository.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcTransactionRepositoryTest {

    @Autowired
    JdbcTransactionRepository repository;

    @BeforeEach
    void setUp(){
        repository.create(new Transaction(1,
                1,
                1,
                1,
                BigDecimal.valueOf(100.00),
                LocalDateTime.now()));
        repository.create(new Transaction(2,
                2,
                2,
                2,
                BigDecimal.valueOf(115.00),
                LocalDateTime.now()));
    }

    @Test
    void shouldFindAllTransactions(){
        List<Transaction> transactions = repository.findAll();
        assertEquals(2, transactions.size());
    }

    @Test
    void shouldFindOneTransaction(){
        Transaction transaction = repository.findById(1).get();
        assertEquals(1, transaction.buyerId());
        assertEquals(1, transaction.sellerId());
    }

    @Test
    void shouldCreateTransaction(){
        Transaction transaction = new Transaction(3, 3, 3,3,
         BigDecimal.valueOf(200.00), LocalDateTime.now());
        repository.create(transaction);
        List<Transaction> transactions = repository.findAll();
        assertEquals(3, transactions.size());
    }

    @Test
    void shouldUpdateTransaction(){
        Transaction transaction = new Transaction(1, 2, 1, 1,
                BigDecimal.valueOf(25.00), LocalDateTime.now());
        repository.update(transaction, 1);

        Transaction pulled = repository.findById(1).get();
        assertEquals(2, pulled.buyerId());
        assertEquals(BigDecimal.valueOf(25.00), pulled.amount());
    }

    @Test
    void shouldDeleteTransaction(){
        repository.delete(1);
        List<Transaction> transactions = repository.findAll();
        assertEquals(1, transactions.size());
    }

    @Test
    void shouldFindTransactionsByBuyerId(){
        List<Transaction> transactions = repository.findByBuyerId(1);
        assertEquals(1, transactions.size());
    }

    @Test
    void shouldFindTransactionsBySellerId(){
        List<Transaction> transactions = repository.findBySellerId(1);
        assertEquals(1, transactions.size());
    }

    @Test
    void shouldFindCount(){
        int count = repository.count();
        assertEquals(2, count);
    }
}