package com.virtusdev.backend_practice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public void createProduct(Product product) {
        productRepository.create(product);
    }

    public void updateProduct(Product product, Integer id) {
        productRepository.update(product, id);
    }

    public void deleteProduct(Integer id) {
        productRepository.delete(id);
    }

    public List<Product> getProductsBySellerId(Integer sellerId) {
        return productRepository.findBySellerId(sellerId);
    }
}
