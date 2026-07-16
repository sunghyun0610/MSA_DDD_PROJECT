package com.example.order.domain.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 주문 애그리거트 루트(Aggregate Root).
 * - Order와 OrderLine은 하나의 애그리거트 = 하나의 트랜잭션 일관성 경계.
 * - 생성은 정적 팩토리 메서드(place)로만 허용 → 불완전한 상태의 Order가 존재할 수 없다.
 * - 상태 변경(cancel)도 도메인 규칙과 함께 애그리거트 내부에서만 일어난다.
 */
public class Order {

    private final String id;
    private final Long customerId;
    private final List<OrderLine> orderLines;
    private final Money totalAmount;
    private final LocalDateTime orderedAt;
    private OrderStatus status;

    private Order(String id, Long customerId, List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 최소 1개 이상이어야 합니다.");
        }
        this.id = id;
        this.customerId = customerId;
        this.orderLines = List.copyOf(orderLines);
        this.totalAmount = calculateTotal(orderLines);
        this.orderedAt = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
    }

    /** 주문 생성 유일한 진입점 */
    public static Order place(String id, Long customerId, List<OrderLine> orderLines) {
        return new Order(id, customerId, orderLines);
    }

    /** 도메인 규칙: 이미 취소된 주문은 다시 취소할 수 없다. */
    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("이미 취소된 주문입니다.");
        }
        this.status = OrderStatus.CANCELLED;
    }

    private static Money calculateTotal(List<OrderLine> lines) {
        return lines.stream()
                .map(OrderLine::lineTotal)
                .reduce(Money.ZERO, Money::plus);
    }

    public String getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
