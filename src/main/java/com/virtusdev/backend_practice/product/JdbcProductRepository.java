package com.virtusdev.backend_practice.product;

import com.virtusdev.backend_practice.common.GenericRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("jdbcProductRepository")
public class JdbcProductRepository implements ProductRepository{

    private final JdbcClient jdbcClient;

    public JdbcProductRepository(JdbcClient jdbcClient){ this.jdbcClient = jdbcClient; }



    public List<Product> findAll() {
        return jdbcClient.sql("select * from product")
                .query(Product.class)
                .list();
    }


    public Optional<Product> findById(Integer id) {
        return jdbcClient.sql("SELECT id, name, description, price, seller_id FROM product WHERE id = :id")
                .param("id", id)
                .query(Product.class)
                .optional();
    }

    public void create(Product product) {
        var updated = jdbcClient.sql("INSERT INTO product(id,name,description,price,seller_id) values(?,?,?,?,?)")
                .params(List.of(product.id(), product.name(), product.description(), product.price(), product.sellerId()))
                .update();

        Assert.state(updated == 1, "Failed to create Product: "  + product.name());
    }

    public void update(Product product, Integer id) {
        var updated = jdbcClient.sql("update product set name = ?, description = ?, price = ?, seller_id = ? where id = ?")
                        .params(List.of(product.name(), product.name(), product.description(), product.price(), product.sellerId(), id))
                                .update();

        Assert.state(updated == 1, "Failed to update Product: "  + product.name());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from product where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete Product" + id);
    }

    public void saveAll(List<Product> products) {
        products.forEach(this::create);
    }

    public int count() {
        return jdbcClient.sql("select * from product").query().listOfRows().size();
    }

    public List<Product> findBySellerId(Integer sellerId) {
        return jdbcClient.sql("SELECT * FROM product WHERE seller_id = :sellerId")
                .param("sellerId", sellerId)
                .query(Product.class)
                .list();
    }
}
