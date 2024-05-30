package com.virtusdev.backend_practice.product;

import com.virtusdev.backend_practice.common.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends GenericRepository<Product> {

    List<Product> findAll();

    Optional<Product> findById(Integer id);

    void create(Product product);

    void update(Product product, Integer id);

    void delete(Integer id);

    void saveAll(List<Product> products);

    List<Product> findBySellerId(Integer sellerId);
}
