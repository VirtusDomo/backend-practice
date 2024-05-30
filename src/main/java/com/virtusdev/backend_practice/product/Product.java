package com.virtusdev.backend_practice.product;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record Product(
        @Id
        Integer id,
        String name,
        String description,
        @Positive
        BigDecimal price,
        Integer sellerId
) {
}
