package com.virtusdev.backend_practice.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setPrintAssertionsDescription;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllProducts() {
        Product product1 = new Product(1, "Mock Product 1", "This is Fake Product 1", BigDecimal.valueOf(50.00), 123);
        Product product2 = new Product(2, "Mock Product 2", "This is Fake Product 2", BigDecimal.valueOf(55.00), 123);
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnProductById(){
        Product product1 = new Product(1, "Mock Product 1", "This is Fake Product 1", BigDecimal.valueOf(50.00), 123);
        Product product2 = new Product(2, "Mock Product 2", "This is Fake Product 2", BigDecimal.valueOf(55.00), 123);
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));

        Optional<Product> product = productService.getProductById(1);

        assertThat(product).isPresent();
        assertThat(product.get().id()).isEqualTo(1);
        verify(productRepository, times(1)).findById(1);

    }

    @Test
    void shouldCreateProduct(){
        Product product1 = new Product(1, "Mock Product 1", "This is Fake Product 1", BigDecimal.valueOf(50.00), 123);
        productService.createProduct(product1);

        verify(productRepository, times(1)).create(product1);
    }

    @Test
    void shouldUpdateProduct(){
        Product product1 = new Product(1, "Updated Product 1", "This is Fake Product 1", BigDecimal.valueOf(50.00), 123);
        productService.updateProduct(product1, 1);

        verify(productRepository, times(1)).update(product1,1);
    }

    @Test
    void shouldDeleteProduct(){
        productService.deleteProduct(1);

        verify(productRepository, times(1)).delete(1);
    }

    @Test
    void shouldReturnProductsBySellerId(){
        Product product1 = new Product(1, "Updated Product 1", "This is Fake Product 1", BigDecimal.valueOf(50.00), 123);
        when(productRepository.findBySellerId(123)).thenReturn(List.of(product1));


        List<Product> products = productService.getProductsBySellerId(123);

        verify(productRepository, times(1)).findBySellerId(123);
        assertThat(products.get(0).name()).isEqualTo("Updated Product 1");

    }



}