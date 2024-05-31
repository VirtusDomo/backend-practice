package com.virtusdev.backend_practice.transaction;


import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        @Id
        Integer id,
        Integer buyerId,
        Integer sellerId,
        Integer productId,
        @Positive
        BigDecimal amount,
        LocalDateTime transactionDate
) {
}
