package com.virtusdev.backend_practice.transaction;

import com.virtusdev.backend_practice.user.User;
import com.virtusdev.backend_practice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserService userService){
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Integer id){
        return transactionRepository.findById(id);
    }

    public void createTransaction(Transaction transaction){
        transactionRepository.create(transaction);
    }

    public void updateTransaction(Transaction transaction, Integer id){
        transactionRepository.update(transaction, id);
    }

    public void deleteTransaction(Integer id){
        transactionRepository.delete(id);
    }

    public List<Transaction> getAllTransactionsBySellerId(Integer sellerId){
        return transactionRepository.findBySellerId(sellerId);
    }

    public List<Transaction> getAllTransactionsByBuyerId(Integer buyerId){
        return transactionRepository.findByBuyerId(buyerId);
    }

    public User getBuyerByTransactionId(Integer transactionId){
        return userService.getUserById(transactionRepository.findById(transactionId).get().buyerId());
    }

}
