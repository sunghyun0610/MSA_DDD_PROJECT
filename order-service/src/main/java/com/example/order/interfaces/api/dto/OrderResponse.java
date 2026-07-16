package com.example.order.interfaces.api.dto;

import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderLine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String orderId,
        Long customerId,
        String status,
        BigDecimal totalAmount,
        LocalDateTime orderedAt,
        List<Line> lines
) {
    public record Line(Long productId, String productName, BigDecimal unitPrice, int quantity) {
        static Line from(OrderLine line) {
            return new Line(line.productId(), line.productName(), line.unitPrice().amount(), line.quantity());
        }
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getTotalAmount().amount(),
                order.getOrderedAt(),
                order.getOrderLines().stream().map(Line::from).toList()
        );
    }
}
