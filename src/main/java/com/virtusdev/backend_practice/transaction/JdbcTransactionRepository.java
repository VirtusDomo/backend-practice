package com.virtusdev.backend_practice.transaction;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Repository
public class JdbcTransactionRepository implements TransactionRepository {

    private final JdbcClient jdbcClient;

    public JdbcTransactionRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    public List<Transaction> findAll(){
        return jdbcClient.sql("SELECT * FROM transaction")
                .query(Transaction.class)
                .list();
    }

    public Optional<Transaction> findById(Integer id){
        return jdbcClient.sql("SELECT id, buyer_id, seller_id, product_id, amount" +
                        ", transaction_date FROM transaction WHERE id = :id")
                .param("id", id)
                .query(Transaction.class)
                .optional();
    }

    public void create(Transaction transaction){
        var updated = jdbcClient.sql("INSERT INTO transaction(id,buyer_id,seller_id,product_id,amount," +
                        "transaction_date) values(?,?,?,?,?,?)")
                .params(List.of(transaction.id(), transaction.buyerId(), transaction.sellerId() ,
                        transaction.productId(), transaction.amount(), transaction.transactionDate()))
                .update();

        Assert.state(updated == 1, "Failed to create Transaction: " + transaction.id());
    }

    public void update(Transaction transaction, Integer id){
        var updated = jdbcClient.sql("update transaction set buyer_id = ?, seller_id = ?, product_id = ?," +
                        "amount = ?, transaction_date = ? where id = ?")
                .params(List.of(transaction.buyerId(), transaction.sellerId() ,
                        transaction.productId(), transaction.amount(), transaction.transactionDate(), id))
                .update();

        Assert.state(updated == 1, "Failed to update Transation:" + id);
    }

    public void delete(Integer id){
        var updated = jdbcClient.sql("DELETE FROM transaction WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete Tranasction: " + id);
    }

    public void saveAll(List<Transaction> transactions){
        transactions.forEach(this::create);
    }

    public int count(){
        return jdbcClient.sql("SELECT * FROM transaction").query().listOfRows().size();
    }

    public List<Transaction> findBySellerId(Integer sellerId){
        return jdbcClient.sql("SELECT * FROM transaction WHERE seller_id = :sellerId")
                .param("sellerId", sellerId)
                .query(Transaction.class)
                .list();
    }

    public List<Transaction> findByBuyerId(Integer buyerId){
        return jdbcClient.sql("SELECT * FROM transaction WHERE buyer_id = :buyerId")
                .param("buyerId", buyerId)
                .query(Transaction.class)
                .list();
    }

}
