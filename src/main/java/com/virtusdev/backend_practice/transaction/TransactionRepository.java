package com.virtusdev.backend_practice.transaction;

import com.virtusdev.backend_practice.common.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends GenericRepository<Transaction> {

    List<Transaction> findAll();

    Optional<Transaction> findById(Integer Id);

    void create(Transaction transaction);

    void update(Transaction transaction, Integer id);

    void delete(Integer id);

    void saveAll(List<Transaction> transactions);

    List<Transaction> findBySellerId(Integer sellerId);

    List<Transaction> findByBuyerId(Integer buyerId);

}
