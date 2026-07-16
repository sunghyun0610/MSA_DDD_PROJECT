package com.example.order.application.dto;

import java.util.List;

/**
 * 유스케이스 입력을 표현하는 커맨드 객체.
 * 표현 계층(HTTP 요청)과 응용 계층 사이의 경계를 명확히 한다.
 */
public record PlaceOrderCommand(
        Long customerId,
        List<OrderItem> items
) {
    public record OrderItem(Long productId, int quantity) {
    }
}
