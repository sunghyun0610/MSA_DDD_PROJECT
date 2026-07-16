package com.example.order.interfaces.api.dto;

import com.example.order.application.dto.PlaceOrderCommand;

import java.util.List;

public record PlaceOrderRequest(
        Long customerId,
        List<Item> items
) {
    public record Item(Long productId, int quantity) {
    }

    public PlaceOrderCommand toCommand() {
        return new PlaceOrderCommand(
                customerId,
                items.stream()
                        .map(i -> new PlaceOrderCommand.OrderItem(i.productId(), i.quantity()))
                        .toList()
        );
    }
}
