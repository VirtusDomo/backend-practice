package com.virtusdev.backend_practice.transaction;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TransactionService transactionService;

    private final List<Transaction> transactions = new ArrayList<>();

    @BeforeEach
    void setUp(){
        transactions.add(new Transaction(1,
                1, 1, 1,
                BigDecimal.valueOf(50.00),
                LocalDateTime.now()));
        transactions.add(new Transaction(1,
                1, 1, 1,
                BigDecimal.valueOf(50.00),
                LocalDateTime.now()));
    }

    @Test
    void shouldFindAllTransactions() throws Exception{
        when(transactionService.getAllTransactions()).thenReturn(transactions);
        mvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactions.size())));
        verify(transactionService,times(1)).getAllTransactions();
    }

    @Test
    void shouldFindOneTransaction() throws Exception{
        Transaction transaction = transactions.get(0);
        when(transactionService.getTransactionById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(transaction));
        mvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(transaction.id())))
                .andExpect(jsonPath("$.buyerId", is(transaction.buyerId())))
                .andExpect(jsonPath("$.sellerId", is(transaction.sellerId())))
                .andExpect(jsonPath("$.amount", closeTo(transaction.amount().doubleValue(), 0.01)));
        verify(transactionService, times(1)).getTransactionById(1);
    }

    @Test
    void shouldReturnNotFoundWithInvalidId() throws Exception{
        when(transactionService.getTransactionById(anyInt()))
                .thenReturn(Optional.empty());
        mvc.perform(get("/api/transactions/99"))
                .andExpect(status().isNotFound());

        verify(transactionService,times(1)).getTransactionById(99);
    }

    @Test
    void shouldCreateTransaction() throws Exception{
        var transaction = new Transaction(2, 1, 1, 3, BigDecimal.valueOf(40.00), LocalDateTime.now());
        doNothing().when(transactionService).createTransaction(transaction);
        mvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction))
            )
                .andExpect(status().isCreated());
        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }

    @Test
    void shouldUpdateTransaction() throws Exception{
        var transaction = new Transaction(2, 1, 1, 3, BigDecimal.valueOf(60.00), LocalDateTime.now().plusMinutes(30));
        doNothing().when(transactionService).updateTransaction(any(Transaction.class), eq(1));
        mvc.perform(put("/api/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction))
            )
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).updateTransaction(any(Transaction.class), eq(1));

    }

    @Test
    void shouldDeleteTransaction() throws Exception{
        doNothing().when(transactionService).deleteTransaction(1);
        mvc.perform(delete("/api/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactions.get(0)))
        )
                .andExpect(status().isNoContent());
        verify(transactionService,times(1)).deleteTransaction(1);
    }

    @Test
    void shouldFindAllTransactionsBySellerId() throws Exception{
        when(transactionService.getAllTransactionsBySellerId(anyInt()))
                .thenReturn(transactions);
        mvc.perform(get("/api/transactions/seller/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactions.size())));
        verify(transactionService,times(1)).getAllTransactionsBySellerId(1);
    }

    @Test
    void shouldFindAllTransactionsByBuyerId() throws Exception{
        when(transactionService.getAllTransactionsByBuyerId(anyInt()))
                .thenReturn(transactions);
        mvc.perform(get("/api/transactions/buyer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(transactions.size())));
        verify(transactionService,times(1)).getAllTransactionsByBuyerId(1);
    }
}
