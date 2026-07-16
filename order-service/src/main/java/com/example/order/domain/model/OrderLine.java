package com.example.order.domain.model;

/**
 * 주문 항목 - Order 애그리거트 내부의 값 객체.
 * - 외부에서 OrderLine을 단독으로 조회/수정하지 않는다. 항상 Order를 통해서만 접근한다.
 * - 상품 정보(이름, 가격)는 주문 시점의 스냅샷으로 저장한다.
 *   (이후 상품 가격이 바뀌어도 이미 완료된 주문 금액은 변하지 않아야 하므로)
 */
public record OrderLine(
        Long productId,
        String productName,
        Money unitPrice,
        int quantity
) {
    public OrderLine {
        if (quantity <= 0) {
            throw new IllegalArgumentException("주문 수량은 1 이상이어야 합니다.");
        }
    }

    public Money lineTotal() {
        return unitPrice.multiply(quantity);
    }
}
