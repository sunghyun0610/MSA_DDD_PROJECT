package com.example.product.interfaces.api.dto;

import com.example.product.domain.model.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        int stockQuantity
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice().amount(),
                product.getStockQuantity()
        );
    }
}
