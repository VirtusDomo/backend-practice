package com.virtusdev.backend_practice.config;

import com.virtusdev.backend_practice.common.GenericRepository;
import com.virtusdev.backend_practice.loader.DataLoadConfiguration;
import com.virtusdev.backend_practice.product.Product;
import com.virtusdev.backend_practice.product.ProductRepository;
import com.virtusdev.backend_practice.run.Run;
import com.virtusdev.backend_practice.run.RunRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virtusdev.backend_practice.transaction.Transaction;
import com.virtusdev.backend_practice.transaction.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public DataLoadConfiguration<Run> runDataLoadConfiguration(RunRepository runRepository) {
        return new DataLoadConfiguration<>(runRepository, "runs.json", new TypeReference<List<Run>>() {});
    }

    @Bean
    public DataLoadConfiguration<Product> productDataLoadConfiguration(ProductRepository productRepository) {
        return new DataLoadConfiguration<>(productRepository, "products.json", new TypeReference<List<Product>>() {});
    }

    @Bean
    public DataLoadConfiguration<Transaction> transactionDataLoadConfiguration(TransactionRepository transactionRepository) {
        return new DataLoadConfiguration<>(transactionRepository, "transactions.json", new TypeReference<List<Transaction>>() {});
    }
}
