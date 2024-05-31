package com.virtusdev.backend_practice.transaction;

import com.virtusdev.backend_practice.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllTransactions(){
        Transaction transaction1 = new Transaction(1,1,1,1, BigDecimal.valueOf(50.00), LocalDateTime.now());
        Transaction transaction2 = new Transaction(2,1,1,2, BigDecimal.valueOf(54.00), LocalDateTime.now());
        when(transactionRepository.findAll()).thenReturn(List.of(transaction1, transaction2));

        List<Transaction> transactions = transactionService.getAllTransactions();

        assertThat(transactions).hasSize(2);
        verify(transactionRepository,times(1)).findAll();

    }

    @Test
    void shouldReturnTransactionById() {
        Transaction transaction = new Transaction(1, 101, 201, 560,BigDecimal.valueOf(100.00), LocalDateTime.now());
        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransactionById(1);

        assertThat(result.get()).isEqualTo(transaction);
        verify(transactionRepository, times(1)).findById(1);
    }

    @Test
    void shouldCreateTransaction() {
        Transaction transaction = new Transaction(1, 101, 201, 5, BigDecimal.valueOf(100.00), LocalDateTime.now());
        doNothing().when(transactionRepository).create(transaction);

        transactionService.createTransaction(transaction);

        verify(transactionRepository, times(1)).create(transaction);
    }

    @Test
    void shouldUpdateTransaction() {
        Transaction transaction = new Transaction(1, 101, 201, 1,BigDecimal.valueOf(100.00), LocalDateTime.now());
        doNothing().when(transactionRepository).update(transaction, 1);

        transactionService.updateTransaction(transaction, 1);

        verify(transactionRepository, times(1)).update(transaction, 1);
    }

    @Test
    void shouldDeleteTransaction() {
        doNothing().when(transactionRepository).delete(1);

        transactionService.deleteTransaction(1);

        verify(transactionRepository, times(1)).delete(1);
    }

    @Test
    void shouldReturnAllTransactionsBySellerId(){
        Transaction transaction1 = new Transaction(1,1,1,1, BigDecimal.valueOf(50.00), LocalDateTime.now());
        Transaction transaction2 = new Transaction(2,1,1,2, BigDecimal.valueOf(54.00), LocalDateTime.now());
        when(transactionRepository.findBySellerId(1)).thenReturn(List.of(transaction1, transaction2));

        List<Transaction> transactions = transactionService.getAllTransactionsBySellerId(1);

        verify(transactionRepository, times(1)).findBySellerId(1);
        assertThat(transactions).hasSize(2);
    }

    @Test
    void shouldReturnAllTransactionsByBuyerId(){
        Transaction transaction1 = new Transaction(1,1,1,1, BigDecimal.valueOf(50.00), LocalDateTime.now());
        Transaction transaction2 = new Transaction(2,1,2,2, BigDecimal.valueOf(54.00), LocalDateTime.now());
        Transaction transaction3 = new Transaction(3,1,1,3, BigDecimal.valueOf(90.00), LocalDateTime.now());
        when(transactionRepository.findByBuyerId(1)).thenReturn(List.of(transaction1,transaction2,transaction3));

        List<Transaction> transactions = transactionService.getAllTransactionsByBuyerId(1);

        verify(transactionRepository, times(1)).findByBuyerId(1);
        assertThat(transactions).hasSize(3);
    }

    @Test
    void shouldReturnBuyerFromTransactionId(){
        // Arrange
        Transaction transaction = new Transaction(1, 101, 201, 5, BigDecimal.valueOf(100.00), LocalDateTime.now());
        User user = new User(101, "Leanne Graham", "Bret", "Sincere@april.biz",
                new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", new Geo("-37.3159", "81.1496")),
                "1-770-736-8031 x56442", "hildegard.org",
                new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets"));

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        when(userService.getUserById(101)).thenReturn(user);

        // Act
        User result = transactionService.getBuyerByTransactionId(1);

        // Assert
        assertThat(result).isEqualTo(user);
        verify(transactionRepository, times(1)).findById(1);
        verify(userService, times(1)).getUserById(101);
    }
}



