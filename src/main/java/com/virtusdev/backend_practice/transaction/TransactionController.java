package com.virtusdev.backend_practice.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer id){
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody Transaction transaction){
        transactionService.createTransaction(transaction);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransaction(@RequestBody Transaction transaction, @PathVariable Integer id){
        transactionService.updateTransaction(transaction, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seller/{sellerId}")
    public List<Transaction> getTransactionsBySellerId(@PathVariable Integer sellerId){
        return transactionService.getAllTransactionsBySellerId(sellerId);
    }

    @GetMapping("/buyer/{buyerId}")
    public List<Transaction> getTransactionsByBuyerId(@PathVariable Integer buyerId){
        return transactionService.getAllTransactionsByBuyerId(buyerId);
    }
}
